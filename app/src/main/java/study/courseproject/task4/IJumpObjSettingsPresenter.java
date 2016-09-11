package study.courseproject.task4;

import study.courseproject.task3.IConfig;

interface IJumpObjSettingsPresenter {
    void setView(IJumpObjSettingsView view);

    void seekBarChanged(IConfig.Name name, int value);
    void colorChanged(IConfig.Name name, int value);
}
