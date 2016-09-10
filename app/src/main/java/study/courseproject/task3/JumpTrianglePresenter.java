package study.courseproject.task3;

class JumpTrianglePresenter implements IJumpTrianglePresenter {
    private IJumpTriangleView view;
    private IJumpObjsContainer container;
    private IJumpTriangleModel model;
    private DisplayLimits limits;
    JumpTrianglePresenter(IJumpTriangleView view, IJumpTriangleModel model){
        this.view=view;
        this.model=model;
    }

    @Override
    public IJumpTriangleView getView() {
        return view;
    }

    @Override
    public void setAllCoords(float x, float y, float maxX, float maxY) {
        view.setMaxCoords(maxX, maxY);
        this.limits=view.getLimits();
        x=(Math.min(Math.max(x, limits.minX), limits.maxX)-limits.minX)/(limits.maxX-limits.minX);
        y=(Math.min(Math.max(y, limits.minY), limits.maxY)-limits.minY)/(limits.maxY-limits.minY);
        model.start(x, y);
    }

    @Override
    public void setCoords(float x, float y){
        view.setCoords(
                x*(limits.maxX-limits.minX)+limits.minX,
                y*(limits.maxY-limits.minY)+limits.minY
        );
    }

    @Override
    public void notifyStop() {
        this.container.remove(this);
    }

    void setParent(IJumpObjsContainer container) {
        this.container=container;
    }
}
