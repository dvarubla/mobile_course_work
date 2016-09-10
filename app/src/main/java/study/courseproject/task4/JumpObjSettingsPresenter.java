package study.courseproject.task4;

import study.courseproject.task3.IConfig;

import java.util.HashMap;
import java.util.Map;

class JumpObjSettingsPresenter implements IJumpObjSettingsPresenter {
    private double EPSILON=1e-6;

    private HashMap<IConfig.Name, DoubleConstraint> map;
    private IJumpObjSettingsView view;
    private IJumpObjSettingsModel model;

    JumpObjSettingsPresenter(IJumpObjSettingsView view, IJumpObjSettingsModel model){
        this.view=view;
        this.model=model;
        initHashMap();
    }

    private void initHashMap(){
        map=new HashMap<>();
        map.put(IConfig.Name.ACCEL, new DoubleConstraint(0, 0.7));
        map.put(IConfig.Name.HORIZ_SPEED, new DoubleConstraint(0, 0.7));
        map.put(IConfig.Name.FRICTION_COEFF, new DoubleConstraint(0, 1));
        map.put(IConfig.Name.ENERGY_LOSS, new DoubleConstraint(0, 1));
        view.setConstraints(map);
        for(Map.Entry<IConfig.Name, DoubleConstraint> entry: map.entrySet()){
            view.setDouble(entry.getKey(), model.<Double>get(entry.getKey()));
        }
    }

    @Override
    public boolean textEditChanged(IConfig.Name name, float value) {
        DoubleConstraint c=map.get(name);
        double resValue=0;
        boolean ok=false;
        if(value>=c.min && value<=c.max){
            ok=true;
            resValue=value;
        } else if(Math.abs(value-c.min)<EPSILON){
            resValue=c.min;
            ok=true;
        } else if(Math.abs(value-c.max)<EPSILON){
            resValue=c.max;
            ok=true;
        }
        if(ok){
            model.set(name, resValue);
        }
        return ok;
    }
}
