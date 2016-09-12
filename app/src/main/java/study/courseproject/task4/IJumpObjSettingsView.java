package study.courseproject.task4;

import study.courseproject.task3.ConfigName;

interface IJumpObjSettingsView {
    int MIN=0;
    int MAX=200;
    void setSeekBarValue(ConfigName name, int value);
    void setColor(ConfigName name, int value);
}
