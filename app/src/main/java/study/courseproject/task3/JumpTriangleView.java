package study.courseproject.task3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

class JumpTriangleView extends View implements IJumpTriangleView {
    private Paint paint;
    private Path path;
    private IJumpTrianglePresenter presenter;
    private int color;
    private int width;
    private int height;
    private float maxX;
    private float maxY;
    private float strokeWidth;
    //частное x и y
    private static float XY_PROPORTION=1.0f/1.5f;
    //на сколько надо разделить высоту объекта, чтобы получить толщину линии
    private static float STROKE_WIDTH_DIV=5;
    //на сколько нужно разделить высоту экрана, чтобы получить высоту объекта
    private static float Y_SIZE_DIV=7;

    public JumpTriangleView(Context context) {
        super(context);
        this.setVisibility(View.INVISIBLE);
    }

    void setConfig(IConfig config){
        this.color=config.getValue(Task3ConfigName.OBJ_COLOR);
    }

    //создание треугольника
    private void createPath(){
        path=new Path();
        path.moveTo(strokeWidth/2, height-1-strokeWidth/2);
        path.lineTo((float)(width-1)/3, strokeWidth/2);
        path.lineTo(width-1-strokeWidth/2, height-1-strokeWidth/2);
        path.close();
    }

    //настройка параметров рисования
    private void createPaint(){
        paint=new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onMeasure(int w, int h){
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas c){
        c.drawPath(path, paint);
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
        this.setVisibility(View.VISIBLE);
        this.setX(x-(width-1)/2);
        this.setY(y-(height-1)/2);
    }

    @Override
    public DisplayLimits getLimits(){
        return new DisplayLimits(
                (float)(width-1)/2,
                (float)(height-1)/2,
                maxX-(float)(width-1)/2,
                maxY-(float)(height-1)/2
        );
    }

    @Override
    public void setMaxCoords(float x, float y) {
        maxX=x;
        maxY=y;
        height=(int)(maxY/Y_SIZE_DIV);
        width=(int)(maxY/Y_SIZE_DIV/XY_PROPORTION);
        strokeWidth=(float)height/STROKE_WIDTH_DIV;
        createPaint();
        createPath();
    }
}
