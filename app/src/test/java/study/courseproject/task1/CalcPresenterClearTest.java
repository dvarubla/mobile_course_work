package study.courseproject.task1;

import org.junit.Before;
import org.junit.Test;

import study.courseproject.StrStorage;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static study.courseproject.StrStorage.attachView;

public class CalcPresenterClearTest {
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
    public void testClearSimple(){
        attachView(v, s);
        p.onTextButtonClick("15");

        reset(v);
        attachView(v, s);
        p.onResetClick();
        verify(v).setTextViewText(eq(""), anyBoolean(), eq(false));
    }

    @Test
    public void testClearAfterOp(){
        attachView(v, s);
        p.onTextButtonClick("15");
        p.onOpButtonClick(CalcOpType.PLUS);
        p.onTextButtonClick("222");
        p.onOpButtonClick(CalcOpType.PLUS);
        p.onTextButtonClick("2");
        p.onResetClick();
        p.onTextButtonClick("5");
        p.onOpButtonClick(CalcOpType.PLUS);
        p.onTextButtonClick("5");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.EQ);
        verify(v).setTextViewText(eq("10"), anyBoolean(), eq(false));
    }
}
