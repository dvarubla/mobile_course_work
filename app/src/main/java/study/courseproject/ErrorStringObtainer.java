package study.courseproject;

import android.content.Context;

public class ErrorStringObtainer implements IErrorStringObtainer {
    private Context ctx;

    public ErrorStringObtainer(Context ctx){
        this.ctx=ctx;
    }
    @Override
    public String getString(Exception ex) {
        try {
            throw ex;
        } catch(DivisionByZeroException e){
            return ctx.getString(R.string.division_by_zero);
        } catch(Exception e){
            return "";
        }
    }
}
