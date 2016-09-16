package study.courseproject.task2;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class SnowmanView extends SnowmanGenView{
    private int[] ballColors;
    public SnowmanView(Context context) {
        super(context);
        init();
    }

    public SnowmanView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init(){
        ballColors=new int[]{0,0,0,0};
    }

    //установить цвет шара
    void setBallColor(int ballId, int ballColor){
        ballColors[ballId]=ballColor;
        invalidate();
    }

    //получить цвет шара, если индекс начинается с 1
    private int getBallColor(int ballId){
        return ballColors[ballId-1];
    }

    @Override
    protected void draw_ball1(Canvas c, int fillColor){
        super.draw_ball1(c, getBallColor(1));
    }

    @Override
    protected void draw_ball2(Canvas c, int fillColor){
        super.draw_ball2(c, getBallColor(2));
    }

    @Override
    protected void draw_ball3(Canvas c, int fillColor){
        super.draw_ball3(c, getBallColor(3));
    }

    @Override
    protected void draw_ball4(Canvas c, int fillColor){
        super.draw_ball4(c, getBallColor(4));
    }
}
