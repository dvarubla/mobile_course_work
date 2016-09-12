package study.courseproject.task4;

import study.courseproject.task3.IConfigName;
import study.courseproject.task3.ITask3ConfigName;

import java.util.HashMap;
import java.util.Map;

class JumpObjSettingsPresenter implements IJumpObjSettingsPresenter {
    private static double EPSILON=1e-6;

    private HashMap<IConfigName, DoubleConstraint> doubleMap;
    private ITask3ConfigName colors[];
    private IJumpObjSettingsView view;
    private IJumpObjSettingsModel model;

    JumpObjSettingsPresenter(IJumpObjSettingsModel model){
        this.model=model;
        initMaps();
    }

    @Override
    public void setView(IJumpObjSettingsView view){
        this.view=view;
        initView();
    }

    private void initView(){
        for(Map.Entry<IConfigName, DoubleConstraint> entry: doubleMap.entrySet()){
            view.setSeekBarValue(
                    entry.getKey(),
                    IJumpObjSettingsView.MIN+(int)(
                            (model.<Double>get(entry.getKey())-entry.getValue().min) /
                                    (entry.getValue().max-entry.getValue().min)*(
                                    IJumpObjSettingsView.MAX-IJumpObjSettingsView.MIN)
                    )
            );
        }
        for(ITask3ConfigName colorName: colors){
            view.setColor(colorName, model.<Integer>get(colorName));
        }
    }

    private void initMaps(){
        doubleMap =new HashMap<>();
        doubleMap.put(ITask3ConfigName.ACCEL, new DoubleConstraint(0, 0.7));
        doubleMap.put(ITask3ConfigName.HORIZ_SPEED, new DoubleConstraint(0, 0.7));
        doubleMap.put(ITask3ConfigName.FRICTION_COEFF, new DoubleConstraint(0, 2));
        doubleMap.put(ITask3ConfigName.ENERGY_LOSS, new DoubleConstraint(0, 0.8));
        doubleMap.put(ITask4ConfigName.SOUND_VOLUME, new DoubleConstraint(0, 1));
        colors=new ITask3ConfigName[]{ITask3ConfigName.BG_COLOR, ITask3ConfigName.OBJ_COLOR};
    }

    @Override
    public void seekBarChanged(IConfigName name, int value) {
        DoubleConstraint c= doubleMap.get(name);
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

    @Override
    public void onExit() {
        model.save();
    }

    @Override
    public void colorChanged(IConfigName name, int value) {
        model.set(name, value);
    }

    @Override
    public void onReset() {
        model.reset();
        initView();
    }
}
