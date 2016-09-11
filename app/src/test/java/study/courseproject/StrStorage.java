package study.courseproject;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import study.courseproject.task1.ICalcView;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

public class StrStorage{
    private String str;
    public StrStorage(String str){
        this.str=str;
    }

    private String getStr(){
        return str;
    }

    private void setStr(String str){
        this.str=str;
    }

    public static void attachView(ICalcView v, final StrStorage storage){
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation){
                String str=(String)invocation.getArguments()[0];
                storage.setStr(str);
                return null;
            }
        }).when(v).setTextViewText(anyString(), anyBoolean(), anyBoolean());

        doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation){
                return storage.getStr();
            }
        }).when(v).getTextViewText();
    }
}
