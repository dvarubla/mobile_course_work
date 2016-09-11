package study.courseproject.task4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import study.courseproject.ItemSingleton;
import study.courseproject.ListTasksActivity;
import study.courseproject.R;
import study.courseproject.task3.Config;
import study.courseproject.task3.IConfig;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

public class JumpObjSettingsActivity extends AppCompatActivity implements IJumpObjSettingsView{
    private boolean needSave;

    private class ConstraintField{
        IConfig.Name type;
        DoubleConstraint constraint;
        ConstraintField(IConfig.Name type, DoubleConstraint constraint){
            this.type=type;
            this.constraint=constraint;
        }
    }
    private IJumpObjSettingsPresenter presenter;
    private HashMap<Integer, ConstraintField> constrMap;
    private HashMap<IConfig.Name, Integer> idsMap;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj_settings);
        df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        createParts();
        processDoubleFields();

        findViewById(R.id.gotoView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JumpObjSettingsActivity.this, ConfJumpObjsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void processDoubleFields(){
        for(final HashMap.Entry<Integer, ConstraintField> entry : constrMap.entrySet()){
            final EditText t=((EditText)findViewById(entry.getKey()));
            t.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length()!=0) {
                        if (!presenter.textEditChanged(
                                entry.getValue().type,
                                Float.valueOf(s.toString()))
                                ) {
                                t.setError(getString(
                                        R.string.settings_err_range,
                                        df.format(entry.getValue().constraint.min),
                                        df.format(entry.getValue().constraint.max)
                                ));
                        }
                    } else {
                        t.setError(getString(R.string.settings_empty));
                    }
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

    @Override
    public void setConstraints(HashMap<IConfig.Name, DoubleConstraint> constrMap) {
        this.constrMap =new HashMap<>();
        this.idsMap=new HashMap<>();
        add(R.id.editTextAccel, IConfig.Name.ACCEL, constrMap);
        add(R.id.editTextHorizSpeed, IConfig.Name.HORIZ_SPEED, constrMap);
        add(R.id.editTextEnergyLoss, IConfig.Name.ENERGY_LOSS, constrMap);
        add(R.id.editTextFrictionCoeff, IConfig.Name.FRICTION_COEFF, constrMap);
    }

    @Override
    public void setDouble(IConfig.Name name, double val){
        ((EditText)findViewById(idsMap.get(name))).setText(df.format(val));
    }

    private void add(
            int id,
            IConfig.Name name,
            HashMap<IConfig.Name, DoubleConstraint> constrMap
    ){
        this.constrMap.put(id, new ConstraintField(name, constrMap.get(name)));
        this.idsMap.put(name, id);
    }
}
