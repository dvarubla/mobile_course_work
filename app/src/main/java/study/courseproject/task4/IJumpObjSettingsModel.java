package study.courseproject.task4;


import study.courseproject.task3.ConfigName;

interface IJumpObjSettingsModel {
    <T> void set(ConfigName name, T value);
    <T> T get(ConfigName name);

    void save();

    void reset();
}
