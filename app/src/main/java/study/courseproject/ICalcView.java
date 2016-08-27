package study.courseproject;

public interface ICalcView {
    void showError(String str);

    String getTextViewText();
    void setTextViewText(String str, boolean scrollRight);
}
