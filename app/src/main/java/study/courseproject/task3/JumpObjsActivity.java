package study.courseproject.task3;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import study.courseproject.R;

public class JumpObjsActivity extends Activity{
    private IJumpObjsPresenter presenter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj);
        final RelativeLayout layout= (RelativeLayout) findViewById(R.id.jump_obj_layout);
        presenter=new JumpObjsFact(new JumpTriangleFact(this)).create(layout);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.stop();
    }
}
