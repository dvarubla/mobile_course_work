package study.courseproject.task3;

interface IJumpTrianglePresenter {
    IJumpTriangleView getView();
    void setAllCoords(float x, float y, float maxX, float maxY);

    void setCoords(float x, float y);
}
