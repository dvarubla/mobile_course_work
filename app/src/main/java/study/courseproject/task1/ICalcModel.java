package study.courseproject.task1;

interface ICalcModel {

    void reset();

    void addNumber(String number);

    void replaceNumber(String number);

    void addOperator(CalcOpType type);
}
