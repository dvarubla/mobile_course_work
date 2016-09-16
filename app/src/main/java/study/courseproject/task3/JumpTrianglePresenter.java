package study.courseproject.task3;

class JumpTrianglePresenter implements IJumpTrianglePresenter {
    private IJumpTriangleView view;
    private IJumpObjsContainer container;
    private IJumpTriangleModel model;
    private DisplayLimits limits;

    JumpTrianglePresenter(IJumpTriangleView view){
        this.view=view;
    }

    @Override
    public IJumpTriangleView getView() {
        return view;
    }

    @Override
    public void setTouchCoords(float x, float y, float maxX, float maxY) {
        view.setMaxCoords(maxX, maxY);
        this.limits=view.getLimits();
        //перевод координат касания в координаты модели
        x=(Math.min(Math.max(x, limits.minX), limits.maxX)-limits.minX)/(limits.maxX-limits.minX);
        y=(Math.min(Math.max(y, limits.minY), limits.maxY)-limits.minY)/(limits.maxY-limits.minY);
        model.start(x, y);
    }

    @Override
    public void setCoords(float x, float y){
        //перевод координат модели в координаты экрана
        view.setCoords(
                x*(limits.maxX-limits.minX)+limits.minX,
                y*(limits.maxY-limits.minY)+limits.minY
        );
    }

    @Override
    public void notifyStop() {
        this.container.remove(this);
    }

    @Override
    public void notifyCollide() {}

    void setParent(IJumpObjsContainer container) {
        this.container=container;
    }

    void setModel(IJumpTriangleModel model) {
        this.model = model;
    }
}
