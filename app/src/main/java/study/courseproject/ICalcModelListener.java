package study.courseproject;

public interface ICalcModelListener {
    void notifyResult(String s);
    void notifyError(Exception exc);
}
