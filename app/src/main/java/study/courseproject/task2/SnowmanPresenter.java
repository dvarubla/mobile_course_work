package study.courseproject.task2;

class SnowmanPresenter implements ISnowmanPresenter{
    private ISnowmanView view;
    private ISnowmanModel model;

    @Override
    public void setView(ISnowmanView view){
        this.view=view;
    }

    @Override
    public void notifyColor(int ballId, int color) {
        view.setBallColor(ballId, color);
    }

    @Override
    public void close(){
        model.close();
    }

    @Override
    public void setModel(ISnowmanModel model) {
        this.model = model;
    }
}
