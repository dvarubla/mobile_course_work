package study.courseproject.task4;

import study.courseproject.task3.IConfigName;

interface IJumpObjSettingsModel {
    <T> void set(IConfigName name, T value);
    <T> T get(IConfigName name);

    void save();

    void reset();
}
