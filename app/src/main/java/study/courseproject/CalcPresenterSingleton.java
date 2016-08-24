package study.courseproject;

public class CalcPresenterSingleton {
    private ICalcPresenter presenter;

    private static CalcPresenterSingleton ourInstance = new CalcPresenterSingleton();

    public static CalcPresenterSingleton getInstance() {
        return ourInstance;
    }

    public void setPresenter(ICalcPresenter presenter){
        this.presenter=presenter;
    }

    public boolean hasPresenter(){
        return presenter!=null;
    }

    public ICalcPresenter getMPresenter(){
        return presenter;
    }

    private CalcPresenterSingleton() {
        presenter=null;
    }
}
