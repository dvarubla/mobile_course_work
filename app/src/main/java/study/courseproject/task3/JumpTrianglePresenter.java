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
    public void setCoords(float x, float y) {
        view.setCoords(x, y, container.getMaxX(), container.getMaxY());
    }

    @Override
    public void setParent(IJumpObjsContainer container) {
        this.container=container;
    }
}
