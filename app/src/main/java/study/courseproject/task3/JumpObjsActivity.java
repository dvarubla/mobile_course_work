package study.courseproject.task3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import study.courseproject.R;

public class JumpObjsActivity extends AppCompatActivity {
    private IJumpObjsPresenter presenter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj);
        final RelativeLayout layout=(RelativeLayout)findViewById(R.id.jump_obj_layout);
        IConfig config=new Config();
        config.setDefaults();
        presenter=new JumpObjsFact(new JumpTriangleFact(this, config), config).create(layout);
    }

    @Override
    public void onStop(){
        super.onStop();
        presenter.stop();
    }
}
