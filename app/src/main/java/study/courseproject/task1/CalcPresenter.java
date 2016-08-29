package study.courseproject.task1;

import android.util.Log;

import study.courseproject.R;

public class CalcPresenter implements ICalcPresenter{
    private ICalcView view;
    private ICalcModel model;
    private boolean needClear;
    private boolean textChanged;
    private boolean error;

    public CalcPresenter(ICalcView view, ICalcModel model){
        this.view=view;
        this.model=model;
        needClear=false;
        error=false;
    }

    @Override
    public void onTextButtonClick(String text) {
        addText(text);
    }

    private void addText(String text){
        error=false;
        String prev=(needClear)?"":this.view.getTextViewText();
        if(needClear){
            needClear=false;
        }
        textChanged=true;
        view.setTextViewText(prev+text, true, false);
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

        if((str.length()==0 || error) && type==CalcOpTypes.OpType.MINUS){
            addText("-");
        } else if(str.length()!=0){
            if(textChanged) {
                model.addNumber(str);
                textChanged =false;
            }
            model.addOperator(type);
            needClear = true;
        }
    }

    @Override
    public void onBackspaceClick() {
        error=false;
        if(needClear){
            view.setTextViewText("", true, false);
        } else {
            String prev = view.getTextViewText();
            if (prev.length() != 0) {
                view.setTextViewText(prev.substring(0, prev.length() - 1), true, false);
                textChanged = true;
            }
        }
    }

    @Override
    public void onResetClick() {
        error=false;
        needClear=false;
        model.reset();
        view.setTextViewText("", false, false);
    }

    @Override
    public void notifyResult(String s) {
        view.setTextViewText(s, false, false);
    }

    @Override
    public void notifyError(Exception exc){
        needClear=true;
        error=true;
        try {
            throw exc;
        } catch (SyntaxError syntaxError){
            Log.w(view.getClass().getSimpleName(), Log.getStackTraceString(syntaxError));
            view.setTextViewText(R.string.calc_syntax_error, false, true);
        } catch (DivisionByZeroException divisionByZeroException){
            Log.w(view.getClass().getSimpleName(), Log.getStackTraceString(divisionByZeroException));
            view.setTextViewText(R.string.division_by_zero, false, true);
        } catch (Exception e){
            Log.e(view.getClass().getSimpleName(), Log.getStackTraceString(e));
            view.showError(Log.getStackTraceString(e));
        }
    }
}
