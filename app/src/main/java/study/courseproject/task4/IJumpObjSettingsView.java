package study.courseproject.task4;

import study.courseproject.task3.IConfig;

import java.util.HashMap;

interface IJumpObjSettingsView {
    void setConstraints(HashMap<IConfig.Name, DoubleConstraint> map);

    void setDouble(IConfig.Name name, double val);
}
