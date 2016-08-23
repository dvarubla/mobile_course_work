package study.courseproject;


public interface ICalcPresenter extends ICalcModelListener{
    void onNumberButtonClick(String text);
    void onOpButtonClick(CalcOpTypes.OpType type);
}
