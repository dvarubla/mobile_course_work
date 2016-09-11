package study.courseproject.task1;

interface ICalcModelListener {
    void notifyResult(String s);
    void notifyError(Exception exc);
}
