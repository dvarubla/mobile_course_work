package study.courseproject.task2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import study.courseproject.R;

public class SnowmanActivity extends AppCompatActivity implements ISnowmanView{
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_snowman);
        createParts();
    }

    private void createParts(){
        ISnowmanPresenter presenter = new SnowmanPresenter();
        presenter.setView(this);
        SnowmanModel model=new SnowmanModel(presenter);
    }

    @Override
    public void setBallColor(int ballId, int color){
        ((SnowmanView)findViewById(R.id.snowman_view)).setBallColor(ballId, color);
    }

}
