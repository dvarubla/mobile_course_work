package study.courseproject.task3;

import java.util.concurrent.ExecutorService;

public interface IJumpTriangleModelFact {
    IJumpTriangleModel create(IJumpTriangleModelListener listener, ExecutorService executor);
}
