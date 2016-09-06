package study.courseproject.task2;
import android.util.Log;

class BallRunnable implements Runnable, IBallRunnable{
    private static int NUM_COLOR_PARTS=3;
    private int colorParts[];
    private int dColorParts[];
    private int sleepMsec;
    private IBallRunnableListener listener;

    BallRunnable(int sleepMsec, int initialColor, int deltaColor, IBallRunnableListener listener){
        colorParts=new int[3];
        dColorParts=new int[3];
        this.listener=listener;
        for(int i=0; i<NUM_COLOR_PARTS; i++){
            this.colorParts[i]=(initialColor&0xFF<<(8*i))>>8*i;
            this.dColorParts[i]=(deltaColor&0xFF<<(8*i))>>8*i;
        }
        this.sleepMsec=sleepMsec;
    }

    @Override
    public void run() {
        while(true) {
            int result=0xFF000000;
            for(int i=0; i<NUM_COLOR_PARTS; i++){
                if((colorParts[i]+dColorParts[i])>0xFF || (colorParts[i]+dColorParts[i])<0){
                    dColorParts[i]*=-1;
                }
                colorParts[i]+=dColorParts[i];
                result|=colorParts[i]<<(8*i);
            }
            listener.notifyColorChange(result);
            try {
                Thread.sleep(sleepMsec);
            } catch (InterruptedException e) {
                Log.e(this.getClass().getSimpleName(), "interrupted");
            }
        }
    }
}
