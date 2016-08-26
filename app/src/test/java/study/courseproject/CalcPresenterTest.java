package study.courseproject;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CalcPresenterTest {

    @Test
    public void testOneBackspace(){
        ICalcView v=mock(ICalcView.class);
        CalcPresenter p=new CalcPresenter(v, new CalcModel());
        when(v.getTextViewText()).thenReturn("").thenReturn("1");
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");
        reset(v);
        when(v.getTextViewText()).thenReturn("12");
        p.onBackspaceClick();
        verify(v).setTextViewText(eq("1"), anyBoolean());
    }

    @Test
    public void testMultBackspace(){
        ICalcView v=mock(ICalcView.class);
        CalcPresenter p=new CalcPresenter(v, new CalcModel());
        when(v.getTextViewText()).thenReturn("").thenReturn("1");
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");

        when(v.getTextViewText()).thenReturn("12");
        p.onBackspaceClick();

        when(v.getTextViewText()).thenReturn("1");
        p.onBackspaceClick();

        when(v.getTextViewText()).thenReturn("").thenReturn("5");
        p.onTextButtonClick("5");
        p.onTextButtonClick("7");

        reset(v);
        when(v.getTextViewText()).thenReturn("57");
        p.onBackspaceClick();
        verify(v).setTextViewText(eq("5"), anyBoolean());
    }


    @Test
    public void testBackspaceAfterEq() throws InterruptedException {
        ICalcView v=mock(ICalcView.class);
        CalcPresenter p=new CalcPresenter(v, new CalcModel());
        when(v.getTextViewText()).thenReturn("").thenReturn("1").thenReturn("12");
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");
        p.onOpButtonClick(CalcOpTypes.OpType.PLUS);
        when(v.getTextViewText()).thenReturn("").thenReturn("4").thenReturn("45");
        p.onTextButtonClick("4");
        p.onTextButtonClick("5");

        final CountDownLatch signal = new CountDownLatch(1);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation){
                signal.countDown();
                return null;
            }
        }).when(v).setTextViewText(anyString(), anyBoolean());

        p.onOpButtonClick(CalcOpTypes.OpType.EQ);
        signal.await(5, TimeUnit.SECONDS);
        reset(v);

        when(v.getTextViewText()).thenReturn("57");
        p.onBackspaceClick();
        when(v.getTextViewText()).thenReturn("5");

        p.onOpButtonClick(CalcOpTypes.OpType.PLUS);
        p.onTextButtonClick("2");

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation){
                String s=(String)invocation.getArguments()[0];
                assertEquals(s, "7");
                signal.countDown();
                return null;
            }
        }).when(v).setTextViewText(anyString(), anyBoolean());

        when(v.getTextViewText()).thenReturn("2");
        p.onOpButtonClick(CalcOpTypes.OpType.EQ);
        signal.await(5, TimeUnit.SECONDS);
    }
}
