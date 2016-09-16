package study.courseproject.task2;

class BallRunnable implements Runnable, IBallRunnable{
    private static int NUM_COLOR_PARTS=3;
    //компонент цвета: r, g, b
    private int colorParts[];
    //изменения частей цвета
    private int dColorParts[];
    //продожительность паузы
    private int sleepMsec;
    private IBallRunnableListener listener;

    BallRunnable(int sleepMsec, int initialColor, int deltaColor, IBallRunnableListener listener){
        colorParts=new int[NUM_COLOR_PARTS];
        dColorParts=new int[NUM_COLOR_PARTS];
        this.listener=listener;
        for(int i=0; i<NUM_COLOR_PARTS; i++){
            //разбиение каждого цвета на компоненты
            this.colorParts[i]=(initialColor&0xFF<<(8*i))>>8*i;
            this.dColorParts[i]=(deltaColor&0xFF<<(8*i))>>8*i;
        }
        this.sleepMsec=sleepMsec;
    }

    @Override
    public void run() {
        boolean running=true;
        while (running) {
            //итоговый цвет
            int result = 0xFF000000;
            for (int i = 0; i < NUM_COLOR_PARTS; i++) {
                //нужно изменять компонент цвета в другую сторону
                if ((colorParts[i] + dColorParts[i]) > 0xFF || (colorParts[i] + dColorParts[i]) < 0) {
                    dColorParts[i] *= -1;
                }
                colorParts[i] += dColorParts[i];
                //добавление в итоговый цвет
                result |= colorParts[i] << (8 * i);
            }
            //уведомить об изменении цвета
            listener.notifyColorChange(result);
            //пауза
            try {
                Thread.sleep(sleepMsec);
            } catch (InterruptedException e) {
                running=false;
            }
        }
    }
}
