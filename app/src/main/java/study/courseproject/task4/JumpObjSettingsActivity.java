package study.courseproject.task4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import study.courseproject.R;
import study.courseproject.task3.Config;
import study.courseproject.task3.IConfig;

import java.util.HashMap;

public class JumpObjSettingsActivity extends AppCompatActivity implements IJumpObjSettingsView{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_obj_settings);

        createParts();
        processDoubleFields();
        final EditText t=((EditText)findViewById(R.id.editTextAccel));

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
                                        entry.getValue().constraint.min,
                                        entry.getValue().constraint.max
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
        IConfig c=new Config();
        c.setDefaults();
        presenter=new JumpObjSettingsPresenter(this, new JumpObjSettingsModel(c));
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
        ((EditText)findViewById(idsMap.get(name))).setText(String.valueOf(val));
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
