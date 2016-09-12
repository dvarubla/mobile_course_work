package study.courseproject.task4;

import study.courseproject.task3.IConfigName;
import study.courseproject.task3.ITask3ConfigName;

interface IJumpObjSettingsView {
    int MIN=0;
    int MAX=200;
    void setSeekBarValue(IConfigName name, int value);
    void setColor(IConfigName name, int value);
}
