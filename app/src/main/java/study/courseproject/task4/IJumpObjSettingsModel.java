package study.courseproject.task4;

import study.courseproject.task3.IConfig;

interface IJumpObjSettingsModel {
    <T> void set(IConfig.Name name, T value);
    <T> T get(IConfig.Name name);
}
