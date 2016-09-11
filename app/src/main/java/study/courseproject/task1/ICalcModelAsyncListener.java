package study.courseproject.task1;

interface ICalcModelAsyncListener extends ICalcModelListener {
    void notifyFinish();
    void notifyWorking();
}
