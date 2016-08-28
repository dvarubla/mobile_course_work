package study.courseproject;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static study.courseproject.StrStorage.attachView;

public class CalcPresenterOperatorsTest {

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
    public void testMod(){
        attachView(v, s);
        p.onTextButtonClick("10");
        p.onOpButtonClick(CalcOpTypes.OpType.MOD);
        p.onTextButtonClick("3");
        p.onOpButtonClick(CalcOpTypes.OpType.MOD);
        p.onTextButtonClick("100");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpTypes.OpType.EQ);
        verify(v).setTextViewText(eq("1"), anyBoolean(), eq(false));
    }

    @Test
    public void testFloatMod(){
        attachView(v, s);
        p.onTextButtonClick("10.6");
        p.onOpButtonClick(CalcOpTypes.OpType.MOD);
        p.onTextButtonClick("3.1");
        p.onOpButtonClick(CalcOpTypes.OpType.MOD);
        p.onTextButtonClick("100.9");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpTypes.OpType.EQ);
        verify(v).setTextViewText(eq("2"), anyBoolean(), eq(false));
    }
}
