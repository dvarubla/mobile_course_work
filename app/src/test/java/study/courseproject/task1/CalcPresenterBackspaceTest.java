package study.courseproject.task1;

import org.junit.Before;
import org.junit.Test;

import study.courseproject.StrStorage;
import study.courseproject.task1.CalcModel;
import study.courseproject.task1.CalcOpTypes;
import study.courseproject.task1.CalcPresenter;
import study.courseproject.task1.ICalcView;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static study.courseproject.StrStorage.attachView;

public class CalcPresenterBackspaceTest {

    ICalcView v;
    StrStorage s;
    CalcPresenter p;

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
        p.onOpButtonClick(CalcOpTypes.OpType.PLUS);
        p.onTextButtonClick("4");
        p.onTextButtonClick("5");

        p.onOpButtonClick(CalcOpTypes.OpType.EQ);

        p.onBackspaceClick();

        p.onOpButtonClick(CalcOpTypes.OpType.PLUS);
        p.onTextButtonClick("2");

        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpTypes.OpType.EQ);
        verify(v).setTextViewText(eq("2"), anyBoolean(), eq(false));
    }

    @Test
    public void testBackspaceAfterPlus() throws InterruptedException {
        attachView(v, s);
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");
        p.onOpButtonClick(CalcOpTypes.OpType.PLUS);
        p.onTextButtonClick("4");
        p.onTextButtonClick("5");

        p.onOpButtonClick(CalcOpTypes.OpType.PLUS);
        reset(v);

        attachView(v, s);
        p.onBackspaceClick();
        verify(v).setTextViewText(eq(""), anyBoolean(), eq(false));
    }


}
