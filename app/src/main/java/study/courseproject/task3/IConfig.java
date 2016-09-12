package study.courseproject.task3;

import java.util.Map;

public interface IConfig {
    void putAll(IConfig config);
    <T> T getValue(ConfigName key);
    <T> void putValue(ConfigName key, T value);
    Map<ConfigName, Object> getMap();
}
