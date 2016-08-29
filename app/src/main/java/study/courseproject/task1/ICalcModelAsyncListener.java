package study.courseproject.task1;

public interface ICalcModelAsyncListener extends ICalcModelListener {
    void notifyFinish();
    void notifyWorking();
}
