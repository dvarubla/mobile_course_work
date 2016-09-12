package study.courseproject.task3;

public interface IJumpTriangleModelListener {
    void setCoords(float x, float y);
    void notifyStop();
    void notifyCollide();
}
