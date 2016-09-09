package study.courseproject.task3;

class JumpTrianglePresenter implements IJumpTrianglePresenter {
    private IJumpTriangleView view;
    private IJumpObjsContainer container;
    private IJumpTriangleModel model;
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
        model.start(view.getLimits(), x, y);
    }

    @Override
    public void setCoords(float x, float y){
        view.setCoords(x, y);
    }

    void setParent(IJumpObjsContainer container) {
        this.container=container;
    }
}
