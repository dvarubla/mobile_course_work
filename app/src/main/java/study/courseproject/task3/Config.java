package study.courseproject.task3;

import java.util.HashMap;
import java.util.Map;

//конфигурация
public class Config implements IConfig{
    private HashMap<ConfigName, Object> map;

    public Config(){
        map=new HashMap<>();
    }

    @Override
    public Map<ConfigName, Object> getMap() {
        return map;
    }

    //импорт из другого объекта
    @Override
    public void putAll(IConfig config) {
        for(Map.Entry<ConfigName, Object> name: config.getMap().entrySet()){
            putValue(name.getKey(), name.getValue());
        }
    }

    @Override
    public <T> T getValue(ConfigName key) {
        if(!map.containsKey(key)){
            throw new NullPointerException("Map does not contain value");
        }
        //noinspection unchecked
        return (T)map.get(key);
    }

    @Override
    public <T> void putValue(ConfigName key, T value) {
        map.put(key, value);
    }
}
