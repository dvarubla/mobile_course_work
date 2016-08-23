package study.courseproject;

public interface ICalcModel {
    void setListener(ICalcModelListener listener);

    void addNumber(String number);
    void addOperator(CalcOpTypes.OpType type);
}
