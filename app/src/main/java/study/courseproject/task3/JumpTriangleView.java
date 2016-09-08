package study.courseproject.task3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

class JumpTriangleView extends View implements IJumpTriangleView {
    private Paint paint;
    private IJumpTrianglePresenter presenter;
    private int color;
    private static int WIDTH=51;
    private static int HEIGHT=31;
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
        setMeasuredDimension(WIDTH, HEIGHT);
    }

    @Override
    protected void onDraw(Canvas c){
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        c.drawRect(0, 0, WIDTH-1, HEIGHT-1, paint);
    }

    void setPresenter(IJumpTrianglePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View getRealView() {
        return this;
    }

    @Override
    public void setCoords(float x, float y, float maxX, float maxY) {
        this.setX(Math.min(Math.max(x-(WIDTH-1)/2, 0), maxX-(WIDTH-1)));
        this.setY(Math.min(Math.max(y-(HEIGHT-1)/2, 0), maxY-(HEIGHT-1)));
    }
}
