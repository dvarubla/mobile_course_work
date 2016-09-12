package study.courseproject.task3;

import java.util.Map;

public interface IConfig {
    void putAll(IConfig config);
    <T> T getValue(IConfigName key);
    <T> void putValue(IConfigName key, T value);
    Map<IConfigName, Object> getMap();
}
