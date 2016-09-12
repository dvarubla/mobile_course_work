package study.courseproject.task3;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;

public class JumpTriangleModel implements IJumpTriangleModel {
    private static double DT=0.25f;
    private static int SLEEP_TMT=100;
    private static int STOP_TMT=2000;
    private static double EPSILON =1e-5;
    private static double MAX_X=1;
    private static double MAX_Y=1;
    private static double MIN_X=0;


    private ExecutorService service;
    private IJumpTriangleModelListener listener;
    private Handler handler;

    private double x, y;
    private double vertSpeed;
    private double horizSpeed;
    private double energyLoss;
    private double frictionCoeff;
    private double accel;

    private double prevVertTimeLeft;
    private double prevHorizDxLeft;

    private boolean friction;
    private boolean stopped;

    public JumpTriangleModel(ExecutorService service, IConfig config){
        this.service = service;
        handler=new Handler(Looper.getMainLooper());
        accel=config.getValue(IConfig.Name.ACCEL);
        horizSpeed=config.getValue(IConfig.Name.HORIZ_SPEED);
        energyLoss=config.getValue(IConfig.Name.ENERGY_LOSS);
        frictionCoeff=config.getValue(IConfig.Name.FRICTION_COEFF);
        friction=false;
        stopped=false;
        prevVertTimeLeft =0;
        prevHorizDxLeft=0;
    }

    @Override
    public void start(final float x, final float y) {
        this.x=x;
        this.y=y;
        vertSpeed = 0;
        setCoords();

        service.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(SLEEP_TMT);
                    } catch (InterruptedException e) {
                        break;
                    }
                    performIteration();
                    setCoords();
                    if(stopped){
                        try {
                            Thread.sleep(STOP_TMT);
                        } catch (InterruptedException e) {
                            break;
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                JumpTriangleModel.this.listener.notifyStop();
                            }
                        });
                        break;
                    }
                }
            }
        });
    }

    private synchronized void setCoords(){
        handler.post(new Runnable() {
            private float x;
            private float y;
            Runnable setCoords(float x, float y){
                this.x=x;
                this.y=y;
                return this;
            }
            @Override
            public void run() {
                listener.setCoords(x,y);
            }
        }.setCoords((float)x,(float)y));
    }

    private void jump(){
        double time=DT+ prevVertTimeLeft;
        double dx = vertSpeed * time + accel * time * time / 2;
        if ((y + dx) > MAX_Y) {
            listener.notifyCollide();
            double elapsedTime = (
                    -vertSpeed + Math.sqrt(vertSpeed * vertSpeed + 2 * accel * (MAX_Y - y))
            ) / accel;
            y = MAX_Y;
            double energy = (float) Math.pow(vertSpeed + accel * (elapsedTime), 2) / 2;
            energy -= energyLoss;
            if (energy < EPSILON) {
                friction=true;
                return;
            }
            vertSpeed = -(float) Math.sqrt(2 * energy);
            prevVertTimeLeft = time - elapsedTime;
        } else {
            y += dx;
            vertSpeed += accel * time;
            prevVertTimeLeft =0;
        }
    }

    private void doHorizMove(){
        if(friction) {
            double sign=Math.signum(horizSpeed);
            horizSpeed=Math.abs(horizSpeed);
            double dx=horizSpeed*DT-frictionCoeff*accel*DT*DT/2;
            if(horizSpeed*dx<EPSILON){
                stopped=true;
                return;
            }
            horizSpeed=sign*(horizSpeed-frictionCoeff*accel*DT);
            x+=sign*dx;
        } else {
            x += horizSpeed * DT;
        }
        x+=prevHorizDxLeft;
        prevHorizDxLeft=0;
        if(x>MAX_X){
            listener.notifyCollide();
            prevHorizDxLeft=-(x-MAX_X);
            x=MAX_X;
            horizSpeed*=-1;
        } else if(x<MIN_X){
            listener.notifyCollide();
            prevHorizDxLeft=MIN_X-x;
            x=MIN_X;
            horizSpeed*=-1;
        }
    }

    private void performIteration(){
        if(!friction){
            jump();
        }
        if(!stopped){
            doHorizMove();
        }
    }

    public void setListener(IJumpTriangleModelListener listener) {
        this.listener = listener;
    }
}
