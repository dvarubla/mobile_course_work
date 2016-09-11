package study.courseproject.task2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import study.courseproject.ItemSingleton;
import study.courseproject.R;

public class SnowmanActivity extends AppCompatActivity implements ISnowmanView{
    private ISnowmanPresenter presenter;
    private boolean needSave;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        needSave=false;
        setContentView(R.layout.activity_snowman);
        createParts();
    }

    private void createParts(){
        ItemSingleton<ISnowmanPresenter> singleton=ItemSingleton.getInstance(ISnowmanPresenter.class);
        if(singleton.hasItem()){
            presenter=singleton.getItem();
            presenter.setView(this);
        } else {
            SnowmanPresenter presenter = new SnowmanPresenter();
            presenter.setView(this);
            presenter.setModel(new SnowmanModel(presenter));
            singleton.setItem(presenter);
            this.presenter=presenter;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        needSave=true;
    }

    @Override
    public void onStop(){
        super.onStop();
        if(!needSave){
            presenter.close();
            ItemSingleton.getInstance(ISnowmanPresenter.class).removeItem();
        }
    }

    @Override
    public void setBallColor(int ballId, int color){
        ((SnowmanView)findViewById(R.id.snowman_view)).setBallColor(ballId, color);
    }

}
