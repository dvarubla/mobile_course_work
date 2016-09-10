package study.courseproject.task3;

interface IConfig {
    enum Names{
        ACCEL,
        HORIZ_SPEED,
        FRICTION_COEFF,
        ENERGY_LOSS,
        BG_COLOR,
        OBJ_COLOR
    }
    <T> T getValue(Names key);
    <T> void putValue(Names key, T value);
    void setDefaults();
}
