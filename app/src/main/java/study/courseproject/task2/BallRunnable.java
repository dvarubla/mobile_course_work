package study.courseproject.task2;
import android.util.Log;

class BallRunnable implements Runnable, IBallRunnable{
    private int color;
    private int deltaColor;
    private int sleepMsec;
    private IBallRunnableListener listener;

    BallRunnable(int sleepMsec, int initialColor, int deltaColor, IBallRunnableListener listener){
        this.listener=listener;
        color=initialColor&0xFFFFFF;
        this.deltaColor=deltaColor;
        this.sleepMsec=sleepMsec;
    }

    @Override
    public void run() {
        while(true) {
            color += this.deltaColor;
            color &= 0xFFFFFF;
            listener.notifyColorChange(0xFF000000 | color);
            try {
                Thread.sleep(sleepMsec);
            } catch (InterruptedException e) {
                Log.e(this.getClass().getSimpleName(), "interrupted");
            }
        }
    }
}
