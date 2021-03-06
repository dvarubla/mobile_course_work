package study.courseproject.task1;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AsyncPresenterTest {
    @Rule
    public ErrorCollector collector= new ErrorCollector();

    private ICalcModel model;
    private CalcModelAsync asyncModel;
    private ICalcPresenter presenter;
    private CalcPresenterAsync asyncPresenter;

    @Before
    public void before(){
        model=mock(ICalcModel.class);
        asyncModel = new CalcModelAsync(model);
        presenter=mock(ICalcPresenter.class);
        asyncPresenter = new CalcPresenterAsync(presenter);
        asyncModel.setListener(asyncPresenter);
    }

    @Test
    public void testSimpleWork() throws InterruptedException {
        final int numOps=3;
        final CountDownLatch signal=new CountDownLatch(1);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                asyncModel.notifyResult("RESULT");
                return null;
            }
        }).when(model).addOperator(any(CalcOpType.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                signal.countDown();
                return null;
            }
        }).when(presenter).notifyResult(anyString());

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                for(int i=0; i<numOps; i++) {
                    asyncModel.addNumber("");
                    asyncModel.addOperator(CalcOpType.PLUS);
                }
                return null;
            }
        }).when(presenter).onOpButtonClick(any(CalcOpType.class));

        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);

        assertTrue(signal.await(1, TimeUnit.SECONDS));
        verify(model, times(3)).addOperator(eq(CalcOpType.PLUS));
        verify(model, times(3)).addNumber(eq(""));
        verify(presenter).notifyResult(eq("RESULT"));
    }

    @Test
    public void testNotifyFinish() throws InterruptedException {
        final SignalContainer signal=new SignalContainer();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                signal.get().countDown();
                return null;
            }
        }).when(model).addOperator(eq(CalcOpType.PLUS));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                asyncModel.addNumber("");
                asyncModel.addOperator(CalcOpType.PLUS);
                return null;
            }
        }).when(presenter).onOpButtonClick(any(CalcOpType.class));

        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);
        assertTrue(signal.get().await(1, TimeUnit.SECONDS));
        signal.reset();
        Thread.sleep(100);
        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);
        assertTrue(signal.get().await(1, TimeUnit.SECONDS));
        signal.reset();
        Thread.sleep(100);
        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);
        assertTrue(signal.get().await(1, TimeUnit.SECONDS));
    }

    @Test
    public void testNotifyError() throws InterruptedException {
        final SignalContainer signal=new SignalContainer();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                if(invocation.getArguments()[0]== CalcOpType.PLUS){
                    asyncModel.notifyError(new Exception());
                } else {
                    asyncModel.notifyResult("");
                }
                return null;
            }
        }).when(model).addOperator(any(CalcOpType.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                signal.get().countDown();
                return null;
            }
        }).when(presenter).notifyError(any(Exception.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                asyncModel.addNumber("");
                asyncModel.addOperator((CalcOpType) invocation.getArguments()[0]);
                return null;
            }
        }).when(presenter).onOpButtonClick(any(CalcOpType.class));

        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);
        asyncPresenter.onOpButtonClick(CalcOpType.MINUS);
        assertTrue(signal.get().await(1, TimeUnit.SECONDS));
        verify(presenter, times(0)).notifyResult(anyString());
    }

    @Test
    public void testNotifyResult() throws InterruptedException {
        final SignalContainer signal=new SignalContainer();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                asyncModel.notifyResult("");
                return null;
            }
        }).when(model).addOperator(any(CalcOpType.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                signal.get().countDown();
                return null;
            }
        }).when(presenter).notifyResult(anyString());

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                asyncModel.addNumber("");
                asyncModel.addOperator(CalcOpType.PLUS);
                return null;
            }
        }).when(presenter).onOpButtonClick(any(CalcOpType.class));

        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);
        assertTrue(signal.get().await(1, TimeUnit.SECONDS));
        signal.reset();

        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);
        assertTrue(signal.get().await(1, TimeUnit.SECONDS));
        signal.reset();

        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);
        assertTrue(signal.get().await(1, TimeUnit.SECONDS));
    }

    @Test
    public void testFreeze() throws InterruptedException {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                asyncModel.notifyResult("");
                return null;
            }
        }).when(model).addOperator(any(CalcOpType.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(presenter).notifyResult(anyString());

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                asyncModel.addNumber("");
                asyncModel.addOperator(CalcOpType.PLUS);
                return null;
            }
        }).when(presenter).onOpButtonClick(any(CalcOpType.class));

        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);
        asyncPresenter.onOpButtonClick(CalcOpType.PLUS);
        verify(presenter, times(1)).onOpButtonClick(any(CalcOpType.class));
    }

    class SignalContainer{
        private CountDownLatch signal;
        SignalContainer(){
            reset();
        }

        void reset(){
            signal=new CountDownLatch(1);
        }

        CountDownLatch get(){
            return signal;
        }
    }
}
