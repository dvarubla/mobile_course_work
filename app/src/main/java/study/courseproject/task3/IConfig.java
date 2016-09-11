package study.courseproject.task3;

public interface IConfig {
    void putAll(IConfig config);

    enum Name {
        ACCEL,
        HORIZ_SPEED,
        FRICTION_COEFF,
        ENERGY_LOSS,
        BG_COLOR,
        OBJ_COLOR
    }
    <T> T getValue(Name key);
    <T> void putValue(Name key, T value);
    void setDefaults();
}
