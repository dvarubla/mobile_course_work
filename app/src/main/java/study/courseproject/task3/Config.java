package study.courseproject.task3;

import java.util.HashMap;

class Config implements IConfig{
    private HashMap<IConfig.Names, Object> map;

    Config(){
        map=new HashMap<>();
    }

    @Override
    public void setDefaults(){
        putValue(Names.ACCEL, 0.7);
        putValue(Names.HORIZ_SPEED, 0.7);
        putValue(Names.FRICTION_COEFF, 0.3);
        putValue(Names.ENERGY_LOSS, 0.01);
        putValue(Names.BG_COLOR, 0xFFFFEDBB);
        putValue(Names.OBJ_COLOR, 0xFF0529B2);
    }

    @Override
    public <T> T getValue(Names key) {
        if(!map.containsKey(key)){
            throw new NullPointerException("map does not contain value");
        }
        //noinspection unchecked
        return (T)map.get(key);
    }

    @Override
    public <T> void putValue(Names key, T value) {
        map.put(key, value);
    }
}
