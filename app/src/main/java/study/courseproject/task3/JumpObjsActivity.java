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
    }

    @Override
    public void onStart(){
        super.onStart();
        final RelativeLayout layout=(RelativeLayout)findViewById(R.id.jump_obj_layout);
        IConfig config=new Config();
        IConfigDefaultsSetter defSetter=new ConfigDefaultsSetter(this);
        defSetter.setDefaults(config);
        presenter=new JumpObjsFact(
                new JumpTriangleFact(this, config, new JumpTriangleModelFact(config)),
                config
        ).create(layout);
    }

    @Override
    public void onStop(){
        super.onStop();
        presenter.stop();
    }
}
