package study.courseproject.task3;

class JumpObjsPresenter implements IJumpObjsPresenter, IJumpObjsContainer {
    private IJumpObjsView view;
    private IJumpTriangleFact fact;
    private float maxX;
    private float maxY;
    JumpObjsPresenter(IJumpObjsView view, IJumpTriangleFact fact) {
        this.view=view;
        this.fact=fact;
    }

    @Override
    public void onTouchDown(float x, float y) {
        IJumpTrianglePresenter p=fact.create();
        p.setParent(this);
        view.addView(p.getView());
        p.setCoords(x, y);
    }

    @Override
    public void setMaxCoords(float x, float y) {
        maxX=x;
        maxY=y;
    }

    @Override
    public float getMaxX() {
        return maxX;
    }

    @Override
    public float getMaxY() {
        return maxY;
    }
}
