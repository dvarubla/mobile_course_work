package study.courseproject.task3;

interface IJumpTrianglePresenter {
    IJumpTriangleView getView();
    void setCoords(float x, float y);
    void setParent(IJumpObjsContainer container);
}
