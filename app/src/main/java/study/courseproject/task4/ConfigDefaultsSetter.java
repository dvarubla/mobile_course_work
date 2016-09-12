package study.courseproject.task4;

import study.courseproject.task3.IConfig;
import study.courseproject.task3.IConfigDefaultsSetter;

class ConfigDefaultsSetter implements IConfigDefaultsSetter {
    private IConfigDefaultsSetter setter;
    ConfigDefaultsSetter(IConfigDefaultsSetter setter){
        this.setter=setter;
    }

    @Override
    public void setDefaults(IConfig config) {
        setter.setDefaults(config);
        config.putValue(ITask4ConfigName.SOUND_VOLUME, 0.5);
    }
}
