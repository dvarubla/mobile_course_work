package study.courseproject.task3;

public class ConfigDefaultsSetter implements IConfigDefaultsSetter{
    public ConfigDefaultsSetter(){}

    @Override
    public void setDefaults(IConfig config) {
        config.putValue(ITask3ConfigName.ACCEL, 0.3);
        config.putValue(ITask3ConfigName.HORIZ_SPEED, 0.2);
        config.putValue(ITask3ConfigName.FRICTION_COEFF, 0.3);
        config.putValue(ITask3ConfigName.ENERGY_LOSS, 0.05);
        config.putValue(ITask3ConfigName.BG_COLOR, 0xFFFFEDBB);
        config.putValue(ITask3ConfigName.OBJ_COLOR, 0xFF0529B2);
    }
}
