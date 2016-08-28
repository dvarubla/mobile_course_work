package study.courseproject.task1;


public interface ICalcPresenter extends ICalcModelListener{
    void onTextButtonClick(String text);
    void setICalcView(ICalcView v);
    void onOpButtonClick(CalcOpTypes.OpType type);

    void onBackspaceClick();
}
