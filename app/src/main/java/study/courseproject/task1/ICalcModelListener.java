package study.courseproject.task1;

public interface ICalcModelListener {
    void notifyResult(String s);
    void notifyError(Exception exc);
}
