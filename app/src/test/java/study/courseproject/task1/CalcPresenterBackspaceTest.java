package study.courseproject.task1;

import org.junit.Before;
import org.junit.Test;

import study.courseproject.StrStorage;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static study.courseproject.StrStorage.attachView;

public class CalcPresenterBackspaceTest {

    private ICalcView v;
    private StrStorage s;
    private CalcPresenter p;

    @Before
    public void before(){
        v=mock(ICalcView.class);
        s=new StrStorage("");
        CalcModel m=new CalcModel();
        p=new CalcPresenter(v, m);
        m.setListener(p);
    }

    @Test
    public void testOneBackspace(){
        attachView(v, s);
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");

        reset(v);
        attachView(v, s);
        p.onBackspaceClick();
        verify(v).setTextViewText(eq("1"), anyBoolean(), eq(false));
    }

    @Test
    public void testMultBackspace(){
        attachView(v, s);
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");
        p.onBackspaceClick();
        p.onBackspaceClick();

        reset(v);
        attachView(v, s);
        p.onTextButtonClick("5");
        p.onBackspaceClick();
        verify(v).setTextViewText(eq("5"), anyBoolean(), eq(false));
    }


    @Test
    public void testBackspaceAfterEq() throws InterruptedException {
        attachView(v, s);
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");
        p.onOpButtonClick(CalcOpType.PLUS);
        p.onTextButtonClick("4");
        p.onTextButtonClick("5");

        p.onOpButtonClick(CalcOpType.EQ);

        p.onBackspaceClick();

        p.onOpButtonClick(CalcOpType.PLUS);
        p.onTextButtonClick("2");

        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.EQ);
        verify(v).setTextViewText(eq("2"), anyBoolean(), eq(false));
    }

    @Test
    public void testBackspaceAfterPlus() throws InterruptedException {
        attachView(v, s);
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");
        p.onOpButtonClick(CalcOpType.PLUS);
        p.onTextButtonClick("4");
        p.onTextButtonClick("5");

        p.onOpButtonClick(CalcOpType.PLUS);
        reset(v);

        attachView(v, s);
        p.onBackspaceClick();
        verify(v).setTextViewText(eq(""), anyBoolean(), eq(false));
    }


}
