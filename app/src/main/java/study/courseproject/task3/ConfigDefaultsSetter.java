package study.courseproject.task3;

public class ConfigDefaultsSetter implements IConfigDefaultsSetter{
    public ConfigDefaultsSetter(){}

    @Override
    public void setDefaults(IConfig config) {
        config.putValue(Task3ConfigName.ACCEL, 0.3);
        config.putValue(Task3ConfigName.HORIZ_SPEED, 0.2);
        config.putValue(Task3ConfigName.FRICTION_COEFF, 0.3);
        config.putValue(Task3ConfigName.ENERGY_LOSS, 0.05);
        config.putValue(Task3ConfigName.BG_COLOR, 0xFFFFEDBB);
        config.putValue(Task3ConfigName.OBJ_COLOR, 0xFF0529B2);
    }
}
