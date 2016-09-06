package study.courseproject.task2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

public class SnowmanView extends SnowmanGenView{
    public SnowmanView(Context context) {
        super(context);
    }

    public SnowmanView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void draw_ball1(Canvas c, int fillColor){
        super.draw_ball1(c, Color.BLUE);
    }

    @Override
    protected void draw_ball2(Canvas c, int fillColor){
        super.draw_ball2(c, Color.BLUE);
    }

    @Override
    protected void draw_ball3(Canvas c, int fillColor){
        super.draw_ball3(c, Color.BLUE);
    }

    @Override
    protected void draw_ball4(Canvas c, int fillColor){
        super.draw_ball4(c, Color.BLUE);
    }
}
