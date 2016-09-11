package study.courseproject.task4;

import study.courseproject.task3.IConfig;

import java.util.HashMap;

interface IJumpObjSettingsView {
    int MIN=0;
    int MAX=200;
    void setSeekBarValue(IConfig.Name name, int value);
    void setColor(IConfig.Name name, int value);
}
