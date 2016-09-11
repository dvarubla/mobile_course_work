package study.courseproject.task3;

import java.util.HashMap;

public class Config implements IConfig{
    private HashMap<Name, Object> map;

    public Config(){
        map=new HashMap<>();
    }

    @Override
    public void setDefaults(){
        putValue(Name.ACCEL, 0.3);
        putValue(Name.HORIZ_SPEED, 0.2);
        putValue(Name.FRICTION_COEFF, 0.3);
        putValue(Name.ENERGY_LOSS, 0.05);
        putValue(Name.BG_COLOR, 0xFFFFEDBB);
        putValue(Name.OBJ_COLOR, 0xFF0529B2);
    }

    @Override
    public void putAll(IConfig config) {
        for(IConfig.Name name: IConfig.Name.values()){
            putValue(name, config.getValue(name));
        }
    }

    @Override
    public <T> T getValue(Name key) {
        if(!map.containsKey(key)){
            throw new NullPointerException("map does not contain value");
        }
        //noinspection unchecked
        return (T)map.get(key);
    }

    @Override
    public <T> void putValue(Name key, T value) {
        map.put(key, value);
    }
}
