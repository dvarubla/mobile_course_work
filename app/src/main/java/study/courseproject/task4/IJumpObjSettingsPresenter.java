package study.courseproject.task4;

import study.courseproject.task3.IConfigName;

interface IJumpObjSettingsPresenter {
    void setView(IJumpObjSettingsView view);

    void seekBarChanged(IConfigName name, int value);
    void onExit();
    void colorChanged(IConfigName name, int value);

    void onReset();
}
