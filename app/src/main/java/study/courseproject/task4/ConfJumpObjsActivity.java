package study.courseproject.task4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import study.courseproject.ItemSingleton;
import study.courseproject.R;
import study.courseproject.task3.*;

public class ConfJumpObjsActivity extends AppCompatActivity{
    private IJumpObjsPresenter presenter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj);
        final RelativeLayout layout=(RelativeLayout)findViewById(R.id.jump_obj_layout);
        ItemSingleton<IPersistentConfig> configS=ItemSingleton.getInstance(IPersistentConfig.class);
        IPersistentConfig c;
        if(configS.hasItem()) {
            c=configS.getItem();
        } else {
            c = new PersistentConfig(this);
            configS.setItem(c);
        }
        presenter=new JumpObjsFact(new JumpTriangleFact(this, c.getConfig()), c.getConfig()).create(layout);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.stop();
    }
}
