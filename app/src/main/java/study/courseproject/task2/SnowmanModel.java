package study.courseproject.task2;

import android.os.Handler;
import android.os.Looper;

class SnowmanModel implements ISnowmanModel{
    private Handler handler;
    private ISnowmanModelListener listener;
    SnowmanModel(int numBalls, ISnowmanModelListener listener){
        handler=new Handler(Looper.getMainLooper());
        this.listener=listener;
        for(int i=0; i<numBalls; i++){
            createBall(i);
        }
    }

    private void createBall(final int id){
        new Thread(new BallRunnable(500, 0, 0x050505, new IBallRunnableListener() {
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
