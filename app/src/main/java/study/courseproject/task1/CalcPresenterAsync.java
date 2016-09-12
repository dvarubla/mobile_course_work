package study.courseproject.task1;

class CalcPresenterAsync implements ICalcPresenterAsync {
    private ICalcPresenter presenter;
    private boolean working;

    CalcPresenterAsync(ICalcPresenter presenter){
        working=false;
        this.presenter=presenter;
    }

    @Override
    public void notifyWorking() {
        working=true;
    }

    @Override
    public void notifyFinish() {
        working=false;
    }

    @Override
    public void onTextButtonClick(String text) {
        if(!working) {
            presenter.onTextButtonClick(text);
        }
    }

    @Override
    public void setICalcView(ICalcView v) {
        presenter.setICalcView(v);
    }

    @Override
    public void onOpButtonClick(CalcOpType type) {
        if(!working) {
            presenter.onOpButtonClick(type);
        }
    }

    @Override
    public void onBackspaceClick() {
        if(!working) {
            presenter.onBackspaceClick();
        }
    }

    @Override
    public void onResetClick() {
        if(!working){
            presenter.onResetClick();
        }
    }

    @Override
    public void notifyResult(String s) {
        working=false;
        presenter.notifyResult(s);
    }

    @Override
    public void notifyError(Exception exc) {
        working=false;
        presenter.notifyError(exc);
    }
}
