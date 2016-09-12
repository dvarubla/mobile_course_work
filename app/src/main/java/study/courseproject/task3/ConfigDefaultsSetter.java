package study.courseproject.task3;

public class ConfigDefaultsSetter implements IConfigDefaultsSetter{
    public ConfigDefaultsSetter(){}

    @Override
    public void setDefaults(IConfig config) {
        config.putValue(IConfigName.ACCEL, 0.3);
        config.putValue(IConfigName.HORIZ_SPEED, 0.2);
        config.putValue(IConfigName.FRICTION_COEFF, 0.3);
        config.putValue(IConfigName.ENERGY_LOSS, 0.05);
        config.putValue(IConfigName.BG_COLOR, 0xFFFFEDBB);
        config.putValue(IConfigName.OBJ_COLOR, 0xFF0529B2);
    }
}
