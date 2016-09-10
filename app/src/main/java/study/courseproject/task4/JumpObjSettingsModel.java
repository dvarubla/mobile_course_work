package study.courseproject.task4;

import study.courseproject.task3.IConfig;

class JumpObjSettingsModel implements IJumpObjSettingsModel {
    private IConfig config;

    JumpObjSettingsModel(IConfig config){
        this.config=config;
    }
    @Override
    public <T> void set(IConfig.Name name, T value){
        config.putValue(name, value);
    }

    @Override
    public <T> T get(IConfig.Name name) {
        return config.getValue(name);
    }
}
