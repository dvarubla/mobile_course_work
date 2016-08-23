package study.courseproject;


public interface ICalcPresenter extends ICalcModelListener{
    void onTextButtonClick(String text);
    void onOpButtonClick(CalcOpTypes.OpType type);
}
