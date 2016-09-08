package study.courseproject.task3;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import study.courseproject.R;

public class JumpObjsActivity extends Activity{
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj);
        final RelativeLayout layout= (RelativeLayout) findViewById(R.id.jump_obj_layout);
        JumpObjsView v=new JumpObjsView(layout);
        JumpObjsPresenter p=new JumpObjsPresenter(v, new JumpTriangleFact(this));
        v.setPresenter(p);
    }
}
