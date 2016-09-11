package study.courseproject.task2;

interface ISnowmanPresenter extends ISnowmanModelListener{
    void setView(ISnowmanView view);

    void close();
}
