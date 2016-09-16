package study.courseproject.task3;

interface IJumpTrianglePresenter extends IJumpTriangleModelListener{
    IJumpTriangleView getView();
    void setTouchCoords(float x, float y, float maxX, float maxY);
}
