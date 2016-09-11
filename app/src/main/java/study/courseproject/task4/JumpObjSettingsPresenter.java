package study.courseproject.task4;

import study.courseproject.task3.IConfig;

import java.util.HashMap;
import java.util.Map;

class JumpObjSettingsPresenter implements IJumpObjSettingsPresenter {
    private double EPSILON=1e-6;

    private HashMap<IConfig.Name, DoubleConstraint> map;
    private IJumpObjSettingsView view;
    private IJumpObjSettingsModel model;

    JumpObjSettingsPresenter(IJumpObjSettingsModel model){
        this.model=model;
        initHashMap();
    }

    @Override
    public void setView(IJumpObjSettingsView view){
        this.view=view;
        for(Map.Entry<IConfig.Name, DoubleConstraint> entry: map.entrySet()){
            view.setSeekBarValue(
                    entry.getKey(),
                    IJumpObjSettingsView.MIN+(int)(
                    (model.<Double>get(entry.getKey())-entry.getValue().min) /
                    (entry.getValue().max-entry.getValue().min)*(
                    IJumpObjSettingsView.MAX-IJumpObjSettingsView.MIN)
                    )
            );
        }
    }

    private void initHashMap(){
        map=new HashMap<>();
        map.put(IConfig.Name.ACCEL, new DoubleConstraint(0, 0.7));
        map.put(IConfig.Name.HORIZ_SPEED, new DoubleConstraint(0, 0.7));
        map.put(IConfig.Name.FRICTION_COEFF, new DoubleConstraint(0, 2));
        map.put(IConfig.Name.ENERGY_LOSS, new DoubleConstraint(0, 0.8));
    }

    @Override
    public void seekBarChanged(IConfig.Name name, int value) {
        DoubleConstraint c=map.get(name);
        double resValue=c.min+(double)(value-IJumpObjSettingsView.MIN) /
                (IJumpObjSettingsView.MAX-IJumpObjSettingsView.MIN)*
                (c.max-c.min)
                ;
        if(Math.abs(value-c.min)<EPSILON){
            resValue=c.min;
        } else if(Math.abs(value-c.max)<EPSILON){
            resValue=c.max;
        }
        model.set(name, resValue);
    }
}
