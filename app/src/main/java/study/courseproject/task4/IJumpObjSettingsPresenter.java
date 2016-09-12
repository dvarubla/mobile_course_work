package study.courseproject.task4;

import study.courseproject.task3.ConfigName;

interface IJumpObjSettingsPresenter {
    void setView(IJumpObjSettingsView view);

    void seekBarChanged(ConfigName name, int value);
    void onExit();
    void colorChanged(ConfigName name, int value);

    void onReset();
}
