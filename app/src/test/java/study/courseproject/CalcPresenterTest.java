package study.courseproject;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class CalcPresenterTest {

    @Test
    public void testOneBackspace(){
        ICalcView v=mock(ICalcView.class);
        StrStorage s=new StrStorage("");
        attachView(v, s);
        CalcPresenter p=new CalcPresenter(v, new CalcModel());
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");

        reset(v);
        attachView(v, s);
        p.onBackspaceClick();
        verify(v).setTextViewText(eq("1"), anyBoolean());
    }

    @Test
    public void testMultBackspace(){
        ICalcView v=mock(ICalcView.class);
        StrStorage s=new StrStorage("");
        attachView(v, s);
        CalcPresenter p=new CalcPresenter(v, new CalcModel());
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");
        p.onBackspaceClick();
        p.onBackspaceClick();

        reset(v);
        attachView(v, s);
        p.onTextButtonClick("5");
        p.onBackspaceClick();
        verify(v).setTextViewText(eq("5"), anyBoolean());
    }


    @Test
    public void testBackspaceAfterEq() throws InterruptedException {
        ICalcView v=mock(ICalcView.class);
        CalcPresenter p=new CalcPresenter(v, new CalcModel());
        StrStorage s=new StrStorage("");
        attachView(v, s);
        p.onTextButtonClick("1");
        p.onTextButtonClick("2");
        p.onOpButtonClick(CalcOpTypes.OpType.PLUS);
        p.onTextButtonClick("4");
        p.onTextButtonClick("5");

        CountDownLatch signal = new CountDownLatch(1);
        attachViewWithSignal(v, s, signal);

        p.onOpButtonClick(CalcOpTypes.OpType.EQ);
        signal.await(1, TimeUnit.SECONDS);
        reset(v);

        attachView(v, s);
        p.onBackspaceClick();

        p.onOpButtonClick(CalcOpTypes.OpType.PLUS);
        p.onTextButtonClick("2");

        reset(v);
        signal = new CountDownLatch(1);
        attachViewWithSignal(v, s, signal);

        p.onOpButtonClick(CalcOpTypes.OpType.EQ);
        signal.await(1, TimeUnit.SECONDS);
        verify(v).setTextViewText(eq("7"), anyBoolean());
    }

    public void attachView(ICalcView v, final StrStorage storage){
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation){
                String str=(String)invocation.getArguments()[0];
                storage.setStr(str);
                return null;
            }
        }).when(v).setTextViewText(anyString(), anyBoolean());

        doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation){
                return storage.getStr();
            }
        }).when(v).getTextViewText();
    }

    public void attachViewWithSignal(ICalcView v, final StrStorage storage, final CountDownLatch signal){
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation){
                String str=(String)invocation.getArguments()[0];
                storage.setStr(str);
                signal.countDown();
                return null;
            }
        }).when(v).setTextViewText(anyString(), anyBoolean());

        doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation){
                signal.countDown();
                return storage.getStr();
            }
        }).when(v).getTextViewText();
    }

    public class StrStorage{
        private String str;
        StrStorage(String str){
            this.str=str;
        }

        public String getStr(){
            return str;
        }

        public void setStr(String str){
            this.str=str;
        }
    }
}
