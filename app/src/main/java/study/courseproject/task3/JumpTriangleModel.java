package study.courseproject.task3;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;

class JumpTriangleModel implements IJumpTriangleModel {
    private static double DT=0.25f;
    private static int SLEEP_TMT=100;
    private static int STOP_TMT=2000;
    private static double FRICTION_EPSILON=1e-3;
    private static double JUMP_EPSILON=1e-7;
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

    JumpTriangleModel(ExecutorService service, IConfig config){
        this.service = service;
        handler=new Handler(Looper.getMainLooper());
        accel=config.getValue(IConfig.Names.ACCEL);
        horizSpeed=config.getValue(IConfig.Names.HORIZ_SPEED);
        energyLoss=config.getValue(IConfig.Names.ENERGY_LOSS);
        frictionCoeff=config.getValue(IConfig.Names.FRICTION_COEFF);
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
        if(Math.abs((y + dx)-MAX_Y)<JUMP_EPSILON){
            y=MAX_Y;
            friction=true;
            return;
        }
        if ((y + dx) > MAX_Y) {
            double elapsedTime = (
                    -vertSpeed + Math.sqrt(vertSpeed * vertSpeed + 2 * accel * (MAX_Y - y))
            ) / accel;
            y = MAX_Y;
            double energy = (float) Math.pow(vertSpeed + accel * (elapsedTime), 2) / 2;
            energy -= energyLoss;
            if (energy < JUMP_EPSILON) {
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
            double dx=horizSpeed*DT/(1+frictionCoeff*DT*DT/2);
            if(Math.abs(dx)< FRICTION_EPSILON){
                stopped=true;
                return;
            }
            horizSpeed=horizSpeed-frictionCoeff*dx;
            x+=dx;
        } else {
            x += horizSpeed * DT;
        }
        x+=prevHorizDxLeft;
        prevHorizDxLeft=0;
        if(x>MAX_X){
            prevHorizDxLeft=-(x-MAX_X);
            x=MAX_X;
            horizSpeed*=-1;
        } else if(x<MIN_X){
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

    void setListener(IJumpTrianglePresenter listener) {
        this.listener = listener;
    }
}
