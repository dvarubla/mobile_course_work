package study.courseproject.task3;

import java.util.HashMap;

public class Config implements IConfig{
    private HashMap<Name, Object> map;

    public Config(){
        map=new HashMap<>();
    }

    @Override
    public void setDefaults(){
        putValue(Name.ACCEL, 0.7);
        putValue(Name.HORIZ_SPEED, 0.7);
        putValue(Name.FRICTION_COEFF, 0.3);
        putValue(Name.ENERGY_LOSS, 0.01);
        putValue(Name.BG_COLOR, 0xFFFFEDBB);
        putValue(Name.OBJ_COLOR, 0xFF0529B2);
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
