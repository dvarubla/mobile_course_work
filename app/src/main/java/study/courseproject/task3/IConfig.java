package study.courseproject.task3;

interface IConfig {
    double getAccel();

    void setAccel(double accel);

    double getHorizSpeed();

    void setHorizSpeed(double horizSpeed);

    double getFrictionCoeff();

    void setFrictionCoeff(double frictionCoeff);

    int getBgColor();

    void setBgColor(int bgColor);

    int getObjColor();

    void setObjColor(int objColor);

    double getEnergyLoss();

    void setEnergyLoss(double energyLoss);
}
