package study.courseproject.task2;

import android.os.Handler;
import android.os.Looper;

class SnowmanModel implements ISnowmanModel{
    private Handler handler;
    private ISnowmanModelListener listener;

    SnowmanModel(ISnowmanModelListener listener){
        handler=new Handler(Looper.getMainLooper());
        this.listener=listener;
        createBall(0, 80, 0xFF0000, 0x121004);
        createBall(1, 200, 0xFF0000, 0x121004);
        createBall(2, 300, 0x00AA22, 0x050903);
        createBall(3, 300, 0x000011, 0x050505);
    }

    private void createBall(final int id, int sleepMsec, int initialColor, int deltaColor){
        new Thread(new BallRunnable(sleepMsec, initialColor, deltaColor, new IBallRunnableListener() {
            @Override
            public void notifyColorChange(final int color) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.notifyColor(id, color);
                    }
                });
            }
        })).start();
    }
}
