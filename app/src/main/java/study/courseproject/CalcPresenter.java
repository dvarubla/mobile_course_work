package study.courseproject;

public class CalcPresenter implements ICalcPresenter{
    private ICalcView view;
    private ICalcModel model;
    private boolean needClear;
    private boolean textEntered;
    private boolean needReplace;

    CalcPresenter(ICalcView view, ICalcModel model){
        this.view=view;
        this.model=model;
        model.setListener(this);
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
        textEntered=true;
        view.setTextViewText(prev+text, true);
    }

    @Override
    public void setICalcView(ICalcView v) {
        this.view=v;
    }

    @Override
    public void onOpButtonClick(CalcOpTypes.OpType type){
        if(type==null){
            throw new NullPointerException("Op type must not be null");
        }
        String str=view.getTextViewText();
        if(!str.equals("")){
            if(textEntered) {
                model.addNumber(str);
                textEntered=false;
            }
            model.addOperator(type);
            needClear=true;
        } else if(type==CalcOpTypes.OpType.MINUS){
            view.setTextViewText("-", true);
        }
    }

    @Override
    public void onBackspaceClick() {
        String prev=view.getTextViewText();
        view.setTextViewText(prev.substring(0, prev.length()-1), true);
        needReplace=true;
    }

    @Override
    public void notifyResult(String s) {
        view.setTextViewText(s, false);
    }

    @Override
    public void notifyError(Exception exc){
        if(exc instanceof DivisionByZeroException){
            view.setTextViewText("AAAA", true);
        } else {
            view.setTextViewText("Unknown error", true);
        }
    }
}
