package study.courseproject.task3;

import java.util.concurrent.ExecutorService;

interface IJumpTriangleFact {
    IJumpTrianglePresenter create(IJumpObjsContainer container, ExecutorService executor);
}
