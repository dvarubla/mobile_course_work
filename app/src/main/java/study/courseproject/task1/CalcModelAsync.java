package study.courseproject.task1;

import android.os.AsyncTask;
import android.support.v4.util.Pair;

import java.util.LinkedList;

class CalcModelAsync implements ICalcModelAsync, ICalcModelListener{

    //асинхронная задача
    //отдельный класс для уменьшения дублирования кода
    abstract class ModelTask extends AsyncTask<Void, Void, Pair<String,Exception>>{
        ModelTask(){
            super();
        }
        @Override
        protected Pair<String, Exception> doInBackground(Void... voids) {
            doTask();
            //если произошла ошибка, все остальные задачи отменяются
            if(exception !=null){
                cancelTasks(this);
            }
            Pair<String,Exception> ret=new Pair<>(result, exception);
            result=null;
            exception =null;
            //возврат в главный поток
            return ret;
        }

        protected abstract void doTask();

        //выполняется в главном потоке
        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        @Override
        protected  void onPostExecute(Pair<String,Exception> pair){

            if(!tasks.isEmpty()) {
                //удаление текущей (завершённой) задачи
                tasks.removeFirst();
            }
            if(pair.second!=null){
                //произошла ошибка, но теперь снова можно добавлять задачи
                dontAddTasks=false;
                listener.notifyError(pair.second);
            } else if (tasks.isEmpty()) {
                //все задачи завершены, можно сообщить результат
                if (pair.first != null) {
                    //есть какой-то результат
                    listener.notifyResult(pair.first);
                } else {
                    listener.notifyFinish();
                }
            }
        }
    }

    private ICalcModel model;
    private ICalcModelAsyncListener listener;

    //результаты
    private String result;
    private Exception exception;

    //не добавлять задачи
    private boolean dontAddTasks;
    //список задач
    private LinkedList<ModelTask> tasks;

    CalcModelAsync(ICalcModel model){
        tasks=new LinkedList<>();
        dontAddTasks=false;
        this.model=model;
    }

    public void setListener(ICalcModelAsyncListener listener) {
        this.listener=listener;
    }
    //отменить все задачи
    private synchronized void cancelTasks(ModelTask exceptThis){
        dontAddTasks=true;
        for(ModelTask t:tasks){
            if(t!=exceptThis) {
                t.cancel(false);
            }
        }
        tasks.clear();
    }
    //добавить задачу
    private synchronized void addTask(ModelTask task){
        if(!dontAddTasks) {
            listener.notifyWorking();
            tasks.add(task);
            task.execute();
        }
    }

    //асинхронные задачи для модели
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
    public void addOperator(final CalcOpType type) {
        addTask(new ModelTask(){
            @Override
            protected void doTask() {
                model.addOperator(type);
            }
        });
    }

    //вызываются в фоновом потоке
    @Override
    public void notifyResult(String s) {
        result=s;
    }

    @Override
    public void notifyError(Exception exc) {
        this.exception =exc;
    }
}
