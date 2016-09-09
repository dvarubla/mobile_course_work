package study.courseproject.task3;

class JumpTrianglePresenter implements IJumpTrianglePresenter {
    private IJumpTriangleView view;
    private IJumpObjsContainer container;
    JumpTrianglePresenter(IJumpTriangleView view){
        this.view=view;
    }

    @Override
    public IJumpTriangleView getView() {
        return view;
    }

    @Override
    public void setCoords(float x, float y, float maxX, float maxY) {
        view.setMaxCoords(maxX, maxY);
        view.setCoords(x, y);
    }

    @Override
    public void setParent(IJumpObjsContainer container) {
        this.container=container;
    }
}
