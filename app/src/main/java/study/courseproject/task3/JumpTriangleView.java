package study.courseproject.task3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

class JumpTriangleView extends View implements IJumpTriangleView {
    private Paint paint;
    private IJumpTrianglePresenter presenter;
    private int color;
    private int width;
    private int height;
    private float maxX;
    private float maxY;
    private static float XY_PROPORTION=1.0f/1.5f;
    private static float Y_SIZE_DIV=10;

    public JumpTriangleView(Context context) {
        super(context);
        paint=new Paint();
        this.color=0xFFFFFFFF;
    }

    public void setColor(int color){
        this.color=color;
    }

    @Override
    protected void onMeasure(int w, int h){
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas c){
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        c.drawRect(0, 0, width -1, height -1, paint);
    }

    void setPresenter(IJumpTrianglePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View getRealView() {
        return this;
    }

    @Override
    public void setCoords(float x, float y) {
        this.setX(Math.min(Math.max(x-(width -1)/2, 0), maxX-(width -1)));
        this.setY(Math.min(Math.max(y-(height -1)/2, 0), maxY-(height -1)));
    }

    @Override
    public void setMaxCoords(float x, float y) {
        maxX=x;
        maxY=y;
        height=(int)(maxY/Y_SIZE_DIV);
        width=(int)(maxY/Y_SIZE_DIV/XY_PROPORTION);
    }
}
