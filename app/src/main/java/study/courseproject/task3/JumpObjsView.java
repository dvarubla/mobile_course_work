package study.courseproject.task3;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

class JumpObjsView implements IJumpObjsView{
    private RelativeLayout layout;
    JumpObjsView(final RelativeLayout layout){
        this.layout=layout;
        layout.setBackgroundColor(0xFF00FF00);
    }

    @Override
    public void setPresenter(final IJumpObjsPresenter presenter){
        layout.post(new Runnable(){
            @Override
            public void run(){
                presenter.setMaxCoords(
                        layout.getWidth()-layout.getPaddingRight()-layout.getPaddingLeft(),
                        layout.getHeight()-layout.getPaddingBottom()-layout.getPaddingTop()
                );
            }
        });
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    float x=Math.min(
                            Math.max(event.getX(), layout.getPaddingLeft()),
                            layout.getWidth()-layout.getPaddingRight()
                    )-layout.getPaddingLeft();
                    float y=Math.min(
                            Math.max(event.getY(), layout.getPaddingTop()),
                            layout.getHeight()-layout.getPaddingBottom()
                    )-layout.getPaddingTop();
                    presenter.onTouchDown(x, y);
                }
                return true;
            }
        });
    }

    @Override
    public void addView(IJumpTriangleView view) {
        this.layout.addView(view.getRealView());
    }
}
