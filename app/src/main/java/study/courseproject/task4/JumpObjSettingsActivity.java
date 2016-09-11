package study.courseproject.task4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import study.courseproject.ItemSingleton;
import study.courseproject.R;
import study.courseproject.task3.Config;
import study.courseproject.task3.IConfig;

import java.util.HashMap;

public class JumpObjSettingsActivity extends AppCompatActivity implements IJumpObjSettingsView{
    private boolean needSave;
    private IJumpObjSettingsPresenter presenter;
    private HashMap<IConfig.Name, Integer> idsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj_settings);

        initMap();
        processSeekBars();
        createParts();

        findViewById(R.id.gotoView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JumpObjSettingsActivity.this, ConfJumpObjsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void processSeekBars(){
        for(final HashMap.Entry<IConfig.Name, Integer> entry: idsMap.entrySet()){
            SeekBar bar=(SeekBar)findViewById(entry.getValue());
            bar.setMax(MAX);
            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    presenter.seekBarChanged(entry.getKey(), seekBar.getProgress());
                }
            });
        }
    }

    private void createParts(){
        ItemSingleton<IJumpObjSettingsPresenter> s=ItemSingleton.getInstance(IJumpObjSettingsPresenter.class);
        if(s.hasItem()) {
            presenter=s.getItem();
            presenter.setView(this);
        } else {
            ItemSingleton<IConfig> configS=ItemSingleton.getInstance(IConfig.class);
            IConfig c;
            if(configS.hasItem()) {
                c=configS.getItem();
            } else {
                c = new Config();
                c.setDefaults();
                configS.setItem(c);
            }
            presenter = new JumpObjSettingsPresenter(new JumpObjSettingsModel(c));
            presenter.setView(this);
            s.setItem(presenter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        needSave=true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(!needSave){
            ItemSingleton.getInstance(IJumpObjSettingsPresenter.class).removeItem();
        }
    }

    private void initMap() {
        this.idsMap=new HashMap<>();
        add(R.id.seekBarAccel, IConfig.Name.ACCEL);
        add(R.id.seekBarHorizSpeed, IConfig.Name.HORIZ_SPEED);
        add(R.id.seekBarEnergyLoss, IConfig.Name.ENERGY_LOSS);
        add(R.id.seekBarFrictionCoeff, IConfig.Name.FRICTION_COEFF);
    }

    private void add(
            int id,
            IConfig.Name name
    ){
        this.idsMap.put(name, id);
    }

    @Override
    public void setSeekBarValue(IConfig.Name name, int value) {
        ((SeekBar)findViewById(idsMap.get(name))).setProgress(value);
    }
}
