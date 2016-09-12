package study.courseproject.task4;

import android.content.Context;
import study.courseproject.R;
import study.courseproject.Util;
import study.courseproject.task3.IConfig;
import study.courseproject.task3.IConfigDefaultsSetter;

class ConfigDefaultsSetter implements IConfigDefaultsSetter {
    private IConfigDefaultsSetter setter;
    private Context ctx;
    ConfigDefaultsSetter(IConfigDefaultsSetter setter, Context ctx){
        this.setter=setter;
        this.ctx=ctx;
    }

    @Override
    public void setDefaults(IConfig config) {
        setter.setDefaults(config);
        config.putValue(Task4ConfigName.SOUND_VOLUME, (double)Util.getFloatVal(ctx, R.dimen.jo_sound_volume));
    }
}
