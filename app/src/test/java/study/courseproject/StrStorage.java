package study.courseproject;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

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
