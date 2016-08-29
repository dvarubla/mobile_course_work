package study.courseproject.task1;

import android.os.AsyncTask;
import android.support.v4.util.Pair;

import java.util.LinkedList;

public class CalcModelAsync implements ICalcModelAsync, ICalcModelListener{

    abstract class ModelTask extends AsyncTask<Void, Void, Pair<String,Exception>>{
        public ModelTask(){
            super();
        }
        @Override
        protected Pair<String, Exception> doInBackground(Void... voids) {
            doTask();
            if(exc!=null){
                cancelTasks(this);
            }
            Pair<String,Exception> ret=new Pair<>(result,exc);
            result=null;
            exc=null;
            return ret;
        }

        protected abstract void doTask();

        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        @Override
        protected  void onPostExecute(Pair<String,Exception> pair){
            if(!tasks.isEmpty()) {
                tasks.removeFirst();
            }
            if(pair.second!=null){
                dontAddTasks=false;
                listener.notifyError(pair.second);
            } else if (tasks.isEmpty()) {
                if (pair.first != null) {
                    listener.notifyResult(pair.first);
                } else {
                    listener.notifyFinish();
                }
            }
        }
    }

    private ICalcModel model;
    private ICalcModelAsyncListener listener;
    private String result;
    private Exception exc;
    private boolean dontAddTasks;
    private LinkedList<ModelTask> tasks;

    public CalcModelAsync(ICalcModel model){
        tasks=new LinkedList<>();
        dontAddTasks=false;
        this.model=model;
    }

    public void setListener(ICalcModelAsyncListener listener) {
        this.listener=listener;
    }

    private synchronized void cancelTasks(ModelTask exceptThis){
        dontAddTasks=true;
        for(ModelTask t:tasks){
            if(t!=exceptThis) {
                t.cancel(false);
            }
        }
        tasks.clear();
    }

    private synchronized void addTask(ModelTask task){
        if(!dontAddTasks) {
            listener.notifyWorking();
            tasks.add(task);
            task.execute();
        }
    }

    @Override
    public void reset() {
        addTask(new ModelTask(){
            @Override
            protected void doTask() {
                model.reset();
            }
        });
    }

    @Override
    public void addNumber(final String number) {
        addTask(new ModelTask(){
            @Override
            protected void doTask() {
                model.addNumber(number);
            }
        });
    }

    @Override
    public void replaceNumber(final String number) {
        addTask(new ModelTask(){
            @Override
            protected void doTask() {
                model.replaceNumber(number);
            }
        });
    }

    @Override
    public void addOperator(final CalcOpTypes.OpType type) {
        addTask(new ModelTask(){
            @Override
            protected void doTask() {
                model.addOperator(type);
            }
        });
    }

    @Override
    public void notifyResult(String s) {
        result=s;
    }

    @Override
    public void notifyError(Exception exc) {
        this.exc=exc;
    }
}
