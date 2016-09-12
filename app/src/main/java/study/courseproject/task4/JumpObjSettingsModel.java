package study.courseproject.task4;

import study.courseproject.task3.IConfigName;

class JumpObjSettingsModel implements IJumpObjSettingsModel {
    private IPersistentConfig config;

    JumpObjSettingsModel(IPersistentConfig config){
        this.config=config;
    }
    @Override
    public <T> void set(IConfigName name, T value){
        config.getConfig().putValue(name, value);
    }

    @Override
    public <T> T get(IConfigName name) {
        return config.getConfig().getValue(name);
    }

    @Override
    public void save(){
        config.save();
    }

    @Override
    public void reset(){
        config.reset();
    }
}
