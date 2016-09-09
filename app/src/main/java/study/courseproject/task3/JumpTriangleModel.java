package study.courseproject.task3;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;

class JumpTriangleModel implements IJumpTriangleModel {
    private static double DT=0.25f;
    private static int SLEEP_TMT=100;
    private static float ENERGY_LOSS=0;
    private static double EPSILON=1e-5;

    private ExecutorService service;
    private IJumpTrianglePresenter presenter;
    private Handler handler;
    private double x, y;
    private double vertSpeed;
    private double accel;
    private DisplayLimits limits;
    JumpTriangleModel(ExecutorService service){
        this.service = service;
        handler=new Handler(Looper.getMainLooper());
        accel=9.8f;
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

    private void performIteration(){
        double time=DT;
        if(Math.abs(y-limits.maxY)<EPSILON){
            return;
        }
        while(true) {
            double dx = vertSpeed * time + accel * time * time / 2;
            if (Math.abs(dx)>EPSILON && (y + dx) > limits.maxY) {
                double spentTime = (
                        -vertSpeed + Math.sqrt(vertSpeed * vertSpeed + 2*accel*(limits.maxY-y))
                )/accel;
                double energy = (float) Math.pow(vertSpeed + accel * (spentTime), 2) / 2;
                energy -= ENERGY_LOSS;
                vertSpeed = -(float) Math.sqrt(2 * energy);
                y = limits.maxY;
                time=time-spentTime;
            } else {
                y += dx;
                vertSpeed+=accel*time;
                break;
            }
        }
    }

    void setPresenter(IJumpTrianglePresenter presenter) {
        this.presenter=presenter;
    }
}
