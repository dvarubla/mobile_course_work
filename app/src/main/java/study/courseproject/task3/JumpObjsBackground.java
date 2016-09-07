package study.courseproject.task3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

class JumpObjsBackground extends View {
    private Paint paint;
    private int color;
    public JumpObjsBackground(Context context) {
        super(context);
        paint=new Paint();
        this.color=0;
    }

    public void setColor(int color){
        this.color=color;
    }

    @Override
    protected void onDraw(Canvas c){
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        c.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}
