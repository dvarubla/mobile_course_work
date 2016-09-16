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
import study.courseproject.task3.ConfigName;
import study.courseproject.task3.Task3ConfigName;

import java.util.HashMap;

public class JumpObjSettingsActivity extends AppCompatActivity implements IJumpObjSettingsView{
    //для хранения цвета, выбранного в диалоге, и id кнопки
    private class ColorBtn{
        public int id;
        public int color;
        ColorBtn(int id){
            this.id=id;
        }
    }
    //нужно ли сохранять presenter
    private boolean needSave;
    private IJumpObjSettingsPresenter presenter;
    private HashMap<ConfigName, Integer> idsSeekbarMap;
    private HashMap<ConfigName, ColorBtn> idsColorBtnMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj_settings);
    }

    @Override
    public void onStart(){
        super.onStart();
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

    //установить обработчик изменения и максимальное значение
    private void processSeekBars(){
        for(final HashMap.Entry<ConfigName, Integer> entry: idsSeekbarMap.entrySet()){
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
    //установить цвет в виджете для предпросмотра
    private void setPreviewColor(ConfigName name, int color){
        TextView v=(TextView) findViewById(R.id.color_preview);
        if(name== Task3ConfigName.BG_COLOR){
            v.setBackgroundColor(color);
        } else {
            v.setTextColor(color);
        }
    }

    //настроить появление диалога при нажатии на кнопку
    private void processColorBtns(){
        for(final HashMap.Entry<ConfigName, ColorBtn> entry: idsColorBtnMap.entrySet()){
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
                                //сохранить цвет
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
                c = new PersistentConfig(this, new ConfigDefaultsSetter(
                        new study.courseproject.task3.ConfigDefaultsSetter(this), this
                ));
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

    //установить соответствий название параметра-id виджета или объект для хранения цвета.
    private void initMaps() {
        idsSeekbarMap =new HashMap<>();
        idsSeekbarMap.put(Task3ConfigName.ACCEL, R.id.seek_bar_accel);
        idsSeekbarMap.put(Task3ConfigName.HORIZ_SPEED, R.id.seek_bar_horiz_speed);
        idsSeekbarMap.put(Task3ConfigName.ENERGY_LOSS, R.id.seek_bar_energy_loss);
        idsSeekbarMap.put(Task3ConfigName.FRICTION_COEFF, R.id.seek_bar_friction_coeff);
        idsSeekbarMap.put(Task4ConfigName.SOUND_VOLUME, R.id.seek_bar_volume);
        idsColorBtnMap =new HashMap<>();
        idsColorBtnMap.put(Task3ConfigName.BG_COLOR, new ColorBtn(R.id.button_bg_color));
        idsColorBtnMap.put(Task3ConfigName.OBJ_COLOR, new ColorBtn(R.id.button_obj_color));
    }

    //установить значение слайдера
    @Override
    public void setSeekBarValue(ConfigName name, int value) {
        ((SeekBar)findViewById(idsSeekbarMap.get(name))).setProgress(value);
    }

    //установить цвет
    @Override
    public void setColor(ConfigName name, int value) {
        idsColorBtnMap.get(name).color=value;
        setPreviewColor(name, value);
    }
}
