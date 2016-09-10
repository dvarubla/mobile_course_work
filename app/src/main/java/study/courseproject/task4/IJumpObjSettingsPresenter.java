package study.courseproject.task4;

import study.courseproject.task3.IConfig;

interface IJumpObjSettingsPresenter {
    void setView(IJumpObjSettingsView view);

    boolean textEditChanged(IConfig.Name name, float value);
}
