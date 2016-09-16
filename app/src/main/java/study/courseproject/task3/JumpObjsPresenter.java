package study.courseproject.task3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class JumpObjsPresenter implements IJumpObjsPresenter, IJumpObjsContainer {
    private IJumpObjsView view;
    private IJumpTriangleFact fact;
    private float maxX;
    private float maxY;
    private ExecutorService executor;
    JumpObjsPresenter(IJumpObjsView view, IJumpTriangleFact fact) {
        this.view=view;
        this.fact=fact;
        executor=Executors.newCachedThreadPool();
    }

    @Override
    public void onTouchDown(float x, float y) {
        IJumpTrianglePresenter p=fact.create(this, executor);
        view.addView(p.getView());
        p.setTouchCoords(x, y, maxX, maxY);
    }

    @Override
    public void setMaxCoords(float x, float y) {
        maxX=x;
        maxY=y;
    }

    @Override
    public void stop() {
        executor.shutdownNow();
    }

    @Override
    public void remove(IJumpTrianglePresenter p) {
        view.removeView(p.getView());
    }
}
