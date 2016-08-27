package study.courseproject;

import android.util.Log;

public class CalcPresenter implements ICalcPresenter{
    private ICalcView view;
    private ICalcModel model;
    private boolean needClear;
    private boolean textChanged;
    private boolean needReplace;
    private IErrorStringObtainer errStrObtainer;

    CalcPresenter(ICalcView view, ICalcModel model, IErrorStringObtainer errorStringObtainer){
        this.view=view;
        this.model=model;
        this.errStrObtainer =errorStringObtainer;
        needClear=false;
        needReplace=false;
    }

    @Override
    public void onTextButtonClick(String text) {
        String prev=(needClear)?"":this.view.getTextViewText();
        if(needClear){
            needClear=false;
            if(needReplace){
                model.replaceNumber(this.view.getTextViewText());
                needReplace=false;
            }
        }
        textChanged=true;
        view.setTextViewText(prev+text, true);
    }

    @Override
    public void setICalcView(ICalcView v) {
        this.view=v;
    }

    @Override
    public void onOpButtonClick(CalcOpTypes.OpType type){
        if(type==null){
            notifyError(new NullPointerException("Op type must not be null"));
            return;
        }
        String str=view.getTextViewText();
        if(str.length()!=0){
            if(textChanged) {
                model.addNumber(str);
                textChanged =false;
            }
            model.addOperator(type);
            if(type!=CalcOpTypes.OpType.EQ) {
                needClear = true;
            }
        } else if(type==CalcOpTypes.OpType.MINUS){
            view.setTextViewText("-", true);
        }
    }

    @Override
    public void onBackspaceClick() {
        if(needClear){
            view.setTextViewText("", true);
        } else {
            String prev = view.getTextViewText();
            if (prev.length() != 0) {
                view.setTextViewText(prev.substring(0, prev.length() - 1), true);
                textChanged = true;
            }
        }
    }

    @Override
    public void notifyResult(String s) {
        view.setTextViewText(s, false);
    }

    @Override
    public void notifyError(Exception exc){
        needClear=true;
        if(exc instanceof DivisionByZeroException){
            view.setTextViewText(errStrObtainer.getString(exc), true);
        } else {
            Log.e(view.getClass().getSimpleName(), Log.getStackTraceString(exc));
            view.showError(Log.getStackTraceString(exc));
        }
    }
}
