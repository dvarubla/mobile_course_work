package study.courseproject.task1;

import android.os.AsyncTask;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import java.util.Map.Entry;

public class CalcModelAsync implements ICalcModelAsync, ICalcModelListener{

    abstract class ModelTask extends AsyncTask<Void, Void, Entry<String,Exception>>{
        public ModelTask(){
            super();
        }
        @Override
        protected Entry<String,Exception> doInBackground(Void... voids) {
            doTask();
            if(exc!=null){
                cancelTasks(this);
            }
            Entry<String,Exception> ret=new SimpleEntry<>(result,exc);
            result=null;
            exc=null;
            return ret;
        }

        protected abstract void doTask();

        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        @Override
        protected  void onPostExecute(Entry<String,Exception> entry){
            if(!tasks.isEmpty()) {
                tasks.removeFirst();
            }
            if(entry.getValue()!=null){
                dontAddTasks=false;
                listener.notifyError(entry.getValue());
            } else if (tasks.isEmpty()) {
                if (entry.getKey() != null) {
                    listener.notifyResult(entry.getKey());
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
