package study.courseproject.task4;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import study.courseproject.task3.Config;
import study.courseproject.task3.IConfig;

class PersistentConfig implements IPersistentConfig{
    private class ConfigName{
        IConfig.Name intName;
        String strName;
        ConfigName(IConfig.Name intName, String strName){
            this.intName=intName;
            this.strName=strName;
        }
    }
    private static final String PREFS_NAME = "Task4Prefs";
    private Context ctx;
    private IConfig savedConfig;
    private IConfig unsavedConfig;
    private boolean configRead;
    private ConfigName doubleNames[]={
        new ConfigName(IConfig.Name.ACCEL, "accel"),
        new ConfigName(IConfig.Name.HORIZ_SPEED, "horizSpeed"),
        new ConfigName(IConfig.Name.FRICTION_COEFF, "frictionCoeff"),
        new ConfigName(IConfig.Name.ENERGY_LOSS, "energyLoss")
    };
    PersistentConfig(Context ctx){
        this.ctx=ctx;
        configRead=false;
    }

    public IConfig getConfig(){
        if(!configRead){
            SharedPreferences p=ctx.getSharedPreferences(PREFS_NAME, 0);
            IConfig config=new Config();
            config.setDefaults();
            for(ConfigName name: doubleNames){
                if(p.contains(name.strName)){
                    config.putValue(name.intName, Double.longBitsToDouble(p.getLong(name.strName, 0)));
                }
            }
            savedConfig=config;
            unsavedConfig=new Config();
            unsavedConfig.putAll(savedConfig);
            configRead=true;
        }
        return unsavedConfig;
    }

    public void save(){
        SharedPreferences p=ctx.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = p.edit();
        for(ConfigName name: doubleNames){
            editor.putLong(name.strName, Double.doubleToLongBits(unsavedConfig.<Double>getValue(name.intName)));
        }
        editor.apply();
        savedConfig.putAll(unsavedConfig);
    }

    public void reset(){
        unsavedConfig.setDefaults();
    }
}
