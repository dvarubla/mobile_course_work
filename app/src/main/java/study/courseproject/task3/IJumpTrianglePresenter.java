package study.courseproject.task3;

interface IJumpTrianglePresenter {
    IJumpTriangleView getView();
    void setCoords(float x, float y, float maxX, float maxY);

    void setParent(IJumpObjsContainer container);
}
