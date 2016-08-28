package study.courseproject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

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
        verify(v).setTextViewText(eq("1"), anyBoolean(), false);
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
        verify(v).setTextViewText(eq("5"), anyBoolean(), false);
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
        verify(v).setTextViewText(eq("7"), anyBoolean(), false);
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
        verify(v).setTextViewText(eq(""), anyBoolean(), false);
    }

    public void attachView(ICalcView v, final StrStorage storage){
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation){
                String str=(String)invocation.getArguments()[0];
                storage.setStr(str);
                return null;
            }
        }).when(v).setTextViewText(anyString(), anyBoolean(), false);

        doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation){
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
