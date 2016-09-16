package study.courseproject.task2;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SnowmanModel implements ISnowmanModel{
    private static int NUM_BALLS=4;
    private Handler handler;
    private ISnowmanModelListener listener;
    private ExecutorService service;

    SnowmanModel(ISnowmanModelListener listener){
        handler=new Handler(Looper.getMainLooper());
        this.listener=listener;
        service=Executors.newFixedThreadPool(NUM_BALLS);
        createBall(0, 80, 0xFF0000, 0x121004);
        createBall(1, 200, 0xFF0000, 0x121004);
        createBall(2, 300, 0x00AA22, 0x050903);
        createBall(3, 300, 0x000011, 0x050505);
    }

    @Override
    public void close(){
        service.shutdownNow();
    }

    private void createBall(final int id, int sleepMsec, int initialColor, int deltaColor){
        //уведомление об установке цвета в основном потоке
        service.submit(new BallRunnable(sleepMsec, initialColor, deltaColor, new IBallRunnableListener() {
            @Override
            public void notifyColorChange(final int color) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.notifyColorSet(id, color);
                    }
                });
            }
        }));
    }
}
