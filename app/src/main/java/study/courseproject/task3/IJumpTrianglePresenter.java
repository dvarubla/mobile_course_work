package study.courseproject.task3;

interface IJumpTrianglePresenter extends IJumpTriangleModelListener{
    IJumpTriangleView getView();
    void setAllCoords(float x, float y, float maxX, float maxY);
}
