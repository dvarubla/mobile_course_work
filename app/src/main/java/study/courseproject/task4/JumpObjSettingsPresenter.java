package study.courseproject.task4;

import study.courseproject.task3.IConfig;

import java.util.HashMap;
import java.util.Map;

class JumpObjSettingsPresenter implements IJumpObjSettingsPresenter {
    private static double EPSILON=1e-6;

    private HashMap<IConfig.Name, DoubleConstraint> doubleMap;
    private IConfig.Name colors[];
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
        for(Map.Entry<IConfig.Name, DoubleConstraint> entry: doubleMap.entrySet()){
            view.setSeekBarValue(
                    entry.getKey(),
                    IJumpObjSettingsView.MIN+(int)(
                            (model.<Double>get(entry.getKey())-entry.getValue().min) /
                                    (entry.getValue().max-entry.getValue().min)*(
                                    IJumpObjSettingsView.MAX-IJumpObjSettingsView.MIN)
                    )
            );
        }
        for(IConfig.Name colorName: colors){
            view.setColor(colorName, model.<Integer>get(colorName));
        }
    }

    private void initMaps(){
        doubleMap =new HashMap<>();
        doubleMap.put(IConfig.Name.ACCEL, new DoubleConstraint(0, 0.7));
        doubleMap.put(IConfig.Name.HORIZ_SPEED, new DoubleConstraint(0, 0.7));
        doubleMap.put(IConfig.Name.FRICTION_COEFF, new DoubleConstraint(0, 2));
        doubleMap.put(IConfig.Name.ENERGY_LOSS, new DoubleConstraint(0, 0.8));
        colors=new IConfig.Name[]{IConfig.Name.BG_COLOR, IConfig.Name.OBJ_COLOR};
    }

    @Override
    public void seekBarChanged(IConfig.Name name, int value) {
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
    public void colorChanged(IConfig.Name name, int value) {
        model.set(name, value);
    }

    @Override
    public void onReset() {
        model.reset();
        initView();
    }
}
