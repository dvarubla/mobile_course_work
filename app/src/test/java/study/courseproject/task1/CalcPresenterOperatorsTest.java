package study.courseproject.task1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import study.courseproject.R;
import study.courseproject.StrStorage;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static study.courseproject.StrStorage.attachView;

public class CalcPresenterOperatorsTest {

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
    public void testMod(){
        attachView(v, s);
        p.onTextButtonClick("10");
        p.onOpButtonClick(CalcOpType.MOD);
        p.onTextButtonClick("3");
        p.onOpButtonClick(CalcOpType.MOD);
        p.onTextButtonClick("100");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.EQ);
        verify(v).setTextViewText(eq("1"), anyBoolean(), eq(false));
    }

    @Test
    public void testFloatMod(){
        attachView(v, s);
        p.onTextButtonClick("10.6");
        p.onOpButtonClick(CalcOpType.MOD);
        p.onTextButtonClick("3.1");
        p.onOpButtonClick(CalcOpType.MOD);
        p.onTextButtonClick("100.9");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.EQ);
        verify(v).setTextViewText(eq("2"), anyBoolean(), eq(false));
    }

    @Test
    public void testDiv(){
        attachView(v, s);
        p.onTextButtonClick("31");
        p.onOpButtonClick(CalcOpType.DIV);
        p.onTextButtonClick("3");
        p.onOpButtonClick(CalcOpType.DIV);
        p.onTextButtonClick("2");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.EQ);
        verify(v).setTextViewText(eq("5"), anyBoolean(), eq(false));
    }

    @Test
    public void testDivFloat(){
        attachView(v, s);
        p.onTextButtonClick("32.6");
        p.onOpButtonClick(CalcOpType.DIV);
        p.onTextButtonClick("3.1");
        p.onOpButtonClick(CalcOpType.DIV);
        p.onTextButtonClick("2.1");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.EQ);
        verify(v).setTextViewText(eq("5"), anyBoolean(), eq(false));
    }

    @Test
    public void testDivZero(){
        attachView(v, s);
        p.onTextButtonClick("32");
        p.onOpButtonClick(CalcOpType.DIV);
        p.onTextButtonClick("0");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.EQ);
        verify(v).setTextViewText(Matchers.eq(R.string.division_by_zero), anyBoolean(), eq(true));
    }

    @Test
    public void testModZero(){
        attachView(v, s);
        p.onTextButtonClick("32");
        p.onOpButtonClick(CalcOpType.MOD);
        p.onTextButtonClick("0");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.EQ);
        verify(v).setTextViewText(eq(R.string.division_by_zero), anyBoolean(), eq(true));
    }

    @Test
    public void testSyntaxErr(){
        attachView(v, s);
        p.onTextButtonClick("...");
        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.PLUS);

        verify(v).setTextViewText(eq(R.string.calc_syntax_error), anyBoolean(), eq(true));
    }

    @Test
    public void testMinusAfterError() throws InterruptedException {
        attachView(v, s);
        p.onTextButtonClick("....");
        p.onOpButtonClick(CalcOpType.PLUS);

        reset(v);
        attachView(v, s);
        p.onOpButtonClick(CalcOpType.MINUS);
        verify(v).setTextViewText(eq("-"), anyBoolean(), eq(false));
    }
}
