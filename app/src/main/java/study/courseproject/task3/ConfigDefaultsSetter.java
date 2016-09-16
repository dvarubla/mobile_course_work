package study.courseproject.task3;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import study.courseproject.R;
import study.courseproject.Util;

//класс для установки параметров конфигурации
public class ConfigDefaultsSetter implements IConfigDefaultsSetter{
    private Context ctx;
    public ConfigDefaultsSetter(Context ctx){
        this.ctx=ctx;
    }

    @Override
    public void setDefaults(IConfig config) {
        config.putValue(Task3ConfigName.ACCEL, (double)Util.getFloatVal(ctx, R.dimen.jo_accel));
        config.putValue(Task3ConfigName.HORIZ_SPEED, (double)Util.getFloatVal(ctx, R.dimen.jo_horiz_speed));
        config.putValue(Task3ConfigName.FRICTION_COEFF, (double)Util.getFloatVal(ctx, R.dimen.jo_friction_coeff));
        config.putValue(Task3ConfigName.ENERGY_LOSS, (double)Util.getFloatVal(ctx, R.dimen.jo_energy_loss));
        config.putValue(Task3ConfigName.BG_COLOR, ContextCompat.getColor(ctx, R.color.jo_bg_color));
        config.putValue(Task3ConfigName.OBJ_COLOR, ContextCompat.getColor(ctx, R.color.jo_obj_color));
    }
}
