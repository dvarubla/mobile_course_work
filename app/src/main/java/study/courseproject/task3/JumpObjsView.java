package study.courseproject.task3;

import android.animation.Animator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

class JumpObjsView implements IJumpObjsView{
    private RelativeLayout layout;
    JumpObjsView(RelativeLayout layout, IConfig config){
        this.layout=layout;
        layout.setBackgroundColor(config.<Integer>getValue(Task3ConfigName.BG_COLOR));
    }

    void setPresenter(final IJumpObjsPresenter presenter){
        //layout ещё не отобразился на экране, поэтому нужно использовать post
        layout.post(new Runnable(){
            @Override
            public void run(){
                presenter.setMaxCoords(
                        layout.getWidth(),
                        layout.getHeight()
                );
            }
        });
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    //нужно учитывать граничные значения
                    float x=Math.min(event.getX(), layout.getWidth());
                    float y=Math.min(event.getY(), layout.getHeight());
                    presenter.onTouchDown(x, y);
                }
                return true;
            }
        });
    }

    @Override
    public void addView(IJumpTriangleView view) {
        layout.addView(view.getRealView());
    }

    @Override
    public void removeView(final IJumpTriangleView view) {
        //анимация исчезновения
        view.getRealView().animate().setDuration(2000).alpha(0f).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                layout.removeView(view.getRealView());
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
