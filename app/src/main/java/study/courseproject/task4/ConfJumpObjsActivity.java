package study.courseproject.task4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import study.courseproject.ItemSingleton;
import study.courseproject.R;
import study.courseproject.task3.*;

public class ConfJumpObjsActivity extends AppCompatActivity{
    private IJumpObjsPresenter presenter;
    private SoundPlayer player;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj);
    }

    @Override
    public void onStart(){
        super.onStart();
        final RelativeLayout layout=(RelativeLayout)findViewById(R.id.jump_obj_layout);
        ItemSingleton<IPersistentConfig> configS=ItemSingleton.getInstance(IPersistentConfig.class);
        IPersistentConfig c;
        if(configS.hasItem()) {
            c=configS.getItem();
        } else {
            c = new PersistentConfig(this, new ConfigDefaultsSetter(
                    new study.courseproject.task3.ConfigDefaultsSetter(this), this
            ));
            configS.setItem(c);
        }
        player=new SoundPlayer(this, c.getConfig());
        presenter=new JumpObjsFact(
                new JumpTriangleFact(
                        this,
                        c.getConfig(),
                        new JumpTriangleSoundModelFact(c.getConfig(), player)
                ),
                c.getConfig()
        ).create(layout);
    }

    //при паузе должен сразу же выключаться звук
    @Override
    public void onPause(){
        super.onPause();
        player.pause();
    }

    @Override
    public void onResume(){
        super.onResume();
        player.resume();
    }

    @Override
    public void onStop(){
        super.onStop();
        presenter.stop();
    }
}
