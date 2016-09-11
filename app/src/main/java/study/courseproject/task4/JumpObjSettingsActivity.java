package study.courseproject.task4;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import study.courseproject.ItemSingleton;
import study.courseproject.R;
import study.courseproject.task3.IConfig;

import java.util.HashMap;

public class JumpObjSettingsActivity extends AppCompatActivity implements IJumpObjSettingsView{
    private class ColorBtn{
        public int id;
        public int color;
        ColorBtn(int id){
            this.id=id;
        }
    }
    private boolean needSave;
    private IJumpObjSettingsPresenter presenter;
    private HashMap<IConfig.Name, Integer> idsSeekbarMap;
    private HashMap<IConfig.Name, ColorBtn> idsColorBtnMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj_settings);

        initMaps();
        processSeekBars();
        processColorBtns();
        createParts();

        findViewById(R.id.gotoView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JumpObjSettingsActivity.this, ConfJumpObjsActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onReset();
            }
        });
    }

    private void processSeekBars(){
        for(final HashMap.Entry<IConfig.Name, Integer> entry: idsSeekbarMap.entrySet()){
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

    private void setPreviewColor(IConfig.Name name, int color){
        TextView v=(TextView) findViewById(R.id.colorPreview);
        if(name== IConfig.Name.BG_COLOR){
            v.setBackgroundColor(color);
        } else {
            v.setTextColor(color);
        }
    }

    private void processColorBtns(){
        for(final HashMap.Entry<IConfig.Name, ColorBtn> entry: idsColorBtnMap.entrySet()){
            findViewById(entry.getValue().id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ColorPickerDialogBuilder
                        .with(JumpObjSettingsActivity.this)
                        .setTitle(getString(R.string.settings_choose_color))
                        .initialColor(idsColorBtnMap.get(entry.getKey()).color)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(10)
                        .lightnessSliderOnly()
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                            }
                        })
                        .setPositiveButton(getString(R.string.ok), new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                idsColorBtnMap.get(entry.getKey()).color=selectedColor;
                                presenter.colorChanged(entry.getKey(), selectedColor);
                                setPreviewColor(entry.getKey(), selectedColor);
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .build()
                        .show();
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
            ItemSingleton<IPersistentConfig> configS=ItemSingleton.getInstance(IPersistentConfig.class);
            IPersistentConfig c;
            if(configS.hasItem()) {
                c=configS.getItem();
            } else {
                c = new PersistentConfig(this);
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
    public void onStop(){
        super.onStop();
        presenter.onExit();
        if(!needSave){
            ItemSingleton.getInstance(IJumpObjSettingsPresenter.class).removeItem();
        }
    }

    private void initMaps() {
        idsSeekbarMap =new HashMap<>();
        idsSeekbarMap.put(IConfig.Name.ACCEL, R.id.seekBarAccel);
        idsSeekbarMap.put(IConfig.Name.HORIZ_SPEED, R.id.seekBarHorizSpeed);
        idsSeekbarMap.put(IConfig.Name.ENERGY_LOSS, R.id.seekBarEnergyLoss);
        idsSeekbarMap.put(IConfig.Name.FRICTION_COEFF, R.id.seekBarFrictionCoeff);
        idsColorBtnMap =new HashMap<>();
        idsColorBtnMap.put(IConfig.Name.BG_COLOR, new ColorBtn(R.id.buttonBgColor));
        idsColorBtnMap.put(IConfig.Name.OBJ_COLOR, new ColorBtn(R.id.buttonObjColor));
    }

    @Override
    public void setSeekBarValue(IConfig.Name name, int value) {
        ((SeekBar)findViewById(idsSeekbarMap.get(name))).setProgress(value);
    }

    @Override
    public void setColor(IConfig.Name name, int value) {
        idsColorBtnMap.get(name).color=value;
        setPreviewColor(name, value);
    }
}
