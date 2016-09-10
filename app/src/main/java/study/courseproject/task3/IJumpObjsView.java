package study.courseproject.task3;

interface IJumpObjsView {
    void setPresenter(IJumpObjsPresenter presenter);
    void addView(IJumpTriangleView view);
    void removeView(IJumpTriangleView view);
}
