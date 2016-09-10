package study.courseproject.task3;

class Config implements IConfig{
    @SuppressWarnings("WeakerAccess")
    public static double DEF_ACCEL=0.7;
    @SuppressWarnings("WeakerAccess")
    public static double DEF_HORIZ_SPEED=0.7;
    @SuppressWarnings("WeakerAccess")
    public static double DEF_FRICTION_COEFF=0.3;
    @SuppressWarnings("WeakerAccess")
    public static double DEF_ENERGY_LOSS=0.01;
    @SuppressWarnings("WeakerAccess")
    public static int DEF_BG_COLOR=0xFFFFEDBB;
    @SuppressWarnings("WeakerAccess")
    public static int DEF_OBJ_COLOR=0xFF0529B2;

    private double accel;
    private double horizSpeed;
    private double frictionCoeff;
    private double energyLoss;
    private int bgColor;
    private int objColor;

    Config(){
        accel=DEF_ACCEL;
        horizSpeed=DEF_HORIZ_SPEED;
        frictionCoeff=DEF_FRICTION_COEFF;
        bgColor=DEF_BG_COLOR;
        objColor=DEF_OBJ_COLOR;
        energyLoss=DEF_ENERGY_LOSS;
    }

    @Override
    public double getAccel() {
        return accel;
    }

    @Override
    public void setAccel(double accel) {
        this.accel = accel;
    }

    @Override
    public double getHorizSpeed() {
        return horizSpeed;
    }

    @Override
    public void setHorizSpeed(double horizSpeed) {
        this.horizSpeed = horizSpeed;
    }

    @Override
    public double getFrictionCoeff() {
        return frictionCoeff;
    }

    @Override
    public void setFrictionCoeff(double frictionCoeff) {
        this.frictionCoeff = frictionCoeff;
    }

    @Override
    public int getBgColor() {
        return bgColor;
    }

    @Override
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public int getObjColor() {
        return objColor;
    }

    @Override
    public void setObjColor(int objColor) {
        this.objColor = objColor;
    }

    @Override
    public double getEnergyLoss() {
        return energyLoss;
    }

    @Override
    public void setEnergyLoss(double energyLoss) {
        this.energyLoss = energyLoss;
    }
}
