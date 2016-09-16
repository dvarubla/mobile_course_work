package study.courseproject.task4;

import android.content.Context;
import android.content.SharedPreferences;
import study.courseproject.task3.*;

class PersistentConfig implements IPersistentConfig{
    private class ConfigName{
        study.courseproject.task3.ConfigName intName;
        String strName;
        ConfigName(study.courseproject.task3.ConfigName intName, String strName){
            this.intName=intName;
            this.strName=strName;
        }
    }
    //название SharedPreferences
    private static final String PREFS_NAME = "Task4Prefs";
    private Context ctx;
    //сохранённые и несохранённые конфигурации
    private IConfig savedConfig;
    private IConfig unsavedConfig;
    //была ли прочитана конфигурация из настроек
    private boolean configRead;
    private IConfigDefaultsSetter defSetter;
    //названия параметров из перечисления и из SharedPreferences

    private ConfigName doubleNames[]={
        new ConfigName(Task3ConfigName.ACCEL, "accel"),
        new ConfigName(Task3ConfigName.HORIZ_SPEED, "horizSpeed"),
        new ConfigName(Task3ConfigName.FRICTION_COEFF, "frictionCoeff"),
        new ConfigName(Task3ConfigName.ENERGY_LOSS, "energyLoss"),
        new ConfigName(Task4ConfigName.SOUND_VOLUME, "soundVolume")
    };

    private ConfigName intNames[]={
        new ConfigName(Task3ConfigName.BG_COLOR, "bgColor"),
        new ConfigName(Task3ConfigName.OBJ_COLOR, "objColor")
    };

    PersistentConfig(Context ctx, IConfigDefaultsSetter defSetter){
        this.ctx=ctx;
        this.defSetter=defSetter;
        configRead=false;
    }

    //получить настройки
    public IConfig getConfig(){
        if(!configRead){
            SharedPreferences p=ctx.getSharedPreferences(PREFS_NAME, 0);
            IConfig config=new Config();
            defSetter.setDefaults(config);
            //получить вещественные параметры
            for(ConfigName name: doubleNames){
                if(p.contains(name.strName)){
                    config.putValue(name.intName, Double.longBitsToDouble(p.getLong(name.strName, 0)));
                }
            }
            //получить целочисленные параметры
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

    //сохранить настройки
    public void save(){
        SharedPreferences p=ctx.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = p.edit();
        //записать вещественные параметры
        for(ConfigName name: doubleNames){
            editor.putLong(name.strName, Double.doubleToLongBits(unsavedConfig.<Double>getValue(name.intName)));
        }
        //записать целочисленные параметры
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
