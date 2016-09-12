package study.courseproject.task3;

import java.util.HashMap;
import java.util.Map;

public class Config implements IConfig{
    private HashMap<IConfigName, Object> map;

    public Config(){
        map=new HashMap<>();
    }

    @Override
    public Map<IConfigName, Object> getMap() {
        return map;
    }

    @Override
    public void putAll(IConfig config) {
        for(Map.Entry<IConfigName, Object> name: config.getMap().entrySet()){
            putValue(name.getKey(), name.getValue());
        }
    }

    @Override
    public <T> T getValue(IConfigName key) {
        if(!map.containsKey(key)){
            throw new NullPointerException("Map does not contain value");
        }
        //noinspection unchecked
        return (T)map.get(key);
    }

    @Override
    public <T> void putValue(IConfigName key, T value) {
        map.put(key, value);
    }
}
