package study.courseproject.task1;


interface ICalcPresenter extends ICalcModelListener{
    void onTextButtonClick(String text);
    void setICalcView(ICalcView v);
    void onOpButtonClick(CalcOpType type);

    void onBackspaceClick();
    void onResetClick();
}
