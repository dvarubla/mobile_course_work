package study.courseproject.task1;

public interface ICalcModel {

    void addNumber(String number);

    void replaceNumber(String number);

    void addOperator(CalcOpTypes.OpType type);
}
