package study.courseproject.task4;

import android.content.Context;
import android.content.SharedPreferences;
import study.courseproject.task3.*;

class PersistentConfig implements IPersistentConfig{
    private class ConfigName{
        IConfigName intName;
        String strName;
        ConfigName(IConfigName intName, String strName){
            this.intName=intName;
            this.strName=strName;
        }
    }
    private static final String PREFS_NAME = "Task4Prefs";
    private Context ctx;
    private IConfig savedConfig;
    private IConfig unsavedConfig;
    private boolean configRead;
    private IConfigDefaultsSetter defSetter;
    private ConfigName doubleNames[]={
        new ConfigName(ITask3ConfigName.ACCEL, "accel"),
        new ConfigName(ITask3ConfigName.HORIZ_SPEED, "horizSpeed"),
        new ConfigName(ITask3ConfigName.FRICTION_COEFF, "frictionCoeff"),
        new ConfigName(ITask3ConfigName.ENERGY_LOSS, "energyLoss"),
        new ConfigName(ITask4ConfigName.SOUND_VOLUME, "soundVolume")
    };

    private ConfigName intNames[]={
        new ConfigName(ITask3ConfigName.BG_COLOR, "bgColor"),
        new ConfigName(ITask3ConfigName.OBJ_COLOR, "objColor")
    };

    PersistentConfig(Context ctx, IConfigDefaultsSetter defSetter){
        this.ctx=ctx;
        this.defSetter=defSetter;
        configRead=false;
    }

    public IConfig getConfig(){
        if(!configRead){
            SharedPreferences p=ctx.getSharedPreferences(PREFS_NAME, 0);
            IConfig config=new Config();
            defSetter.setDefaults(config);
            for(ConfigName name: doubleNames){
                if(p.contains(name.strName)){
                    config.putValue(name.intName, Double.longBitsToDouble(p.getLong(name.strName, 0)));
                }
            }
            for(ConfigName name: intNames){
                if(p.contains(name.strName)){
                    config.putValue(name.intName, p.getInt(name.strName, 0));
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
        for(ConfigName name: intNames){
            editor.putInt(name.strName, unsavedConfig.<Integer>getValue(name.intName));
        }
        editor.apply();
        savedConfig.putAll(unsavedConfig);
    }

    public void reset(){
        defSetter.setDefaults(unsavedConfig);
    }
}
