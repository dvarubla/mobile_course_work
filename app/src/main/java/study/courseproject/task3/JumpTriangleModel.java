package study.courseproject.task3;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;

class JumpTriangleModel implements IJumpTriangleModel {
    private static double DT=0.25f;
    private static int SLEEP_TMT=100;
    private static double EPSILON=1e-5;

    private ExecutorService service;
    private IJumpTrianglePresenter presenter;
    private Handler handler;

    private double x, y;
    private double vertSpeed;
    private double horizSpeed;
    private float energyLoss;
    private float frictionCoeff;
    private double accel;

    private double prevVertTimeLeft;
    private double prevHorizDxLeft;

    private boolean friction;
    private boolean stopped;

    private DisplayLimits limits;
    JumpTriangleModel(ExecutorService service){
        this.service = service;
        handler=new Handler(Looper.getMainLooper());
        accel=9.8f;
        horizSpeed=10;
        energyLoss=300;
        frictionCoeff=1;
        friction=false;
        stopped=false;
        prevVertTimeLeft =0;
        prevHorizDxLeft=0;
    }

    @Override
    public void start(DisplayLimits limits, final float x, final float y) {
        this.limits = limits;
        this.x = Math.min(Math.max(x, limits.minX), limits.maxX);
        this.y = Math.min(Math.max(y, limits.minY), limits.maxY);
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
                presenter.setCoords(x,y);
            }
        }.setCoords((float)x,(float)y));
    }

    private void jump(){
        double time=DT+ prevVertTimeLeft;
        double dx = vertSpeed * time + accel * time * time / 2;
        if(Math.abs((y + dx)-limits.maxY)<EPSILON){
            y=limits.maxY;
            friction=true;
            return;
        }
        if ((y + dx) > limits.maxY) {
            double elapsedTime = (
                    -vertSpeed + Math.sqrt(vertSpeed * vertSpeed + 2 * accel * (limits.maxY - y))
            ) / accel;
            y = limits.maxY;
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
            double dx=horizSpeed*DT/(1+frictionCoeff*DT*DT/2);
            if(Math.abs(dx)<EPSILON){
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
        if(x>limits.maxX){
            prevHorizDxLeft=-(x-limits.maxX);
            x=limits.maxX;
            horizSpeed*=-1;
        } else if(x<limits.minX){
            prevHorizDxLeft=limits.minX-x;
            x=limits.minX;
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

    void setPresenter(IJumpTrianglePresenter presenter) {
        this.presenter=presenter;
    }
}
