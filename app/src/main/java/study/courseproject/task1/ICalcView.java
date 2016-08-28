package study.courseproject.task1;

public interface ICalcView {
    void showError(String str);

    String getTextViewText();
    void setTextViewText(String str, boolean scrollRight, boolean error);

    void setTextViewText(int str, boolean scrollRight, boolean error);
}
