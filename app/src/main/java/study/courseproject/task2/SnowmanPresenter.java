package study.courseproject.task2;

class SnowmanPresenter implements ISnowmanPresenter{
    private ISnowmanView view;

    @Override
    public void setView(ISnowmanView view){
        this.view=view;
    }

    @Override
    public void notifyColor(int ballId, int color) {
        this.view.setBallColor(ballId, color);
    }
}
