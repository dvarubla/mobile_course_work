package study.courseproject;

import android.os.AsyncTask;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class CalcModelAsync implements ICalcModelAsync, ICalcModelListener{

    class ModelTask extends AsyncTask<Void, Void, Entry<String,Exception>>{
        @Override
        protected Entry<String,Exception> doInBackground(Void... voids) {
            Entry<String,Exception> ret=new SimpleEntry<>(result,exc);
            result=null;
            exc=null;
            return ret;
        }

        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        @Override
        protected  void onPostExecute(Entry<String,Exception> entry){
            if(entry.getKey()!=null){
                listener.notifyResult(entry.getKey());
            } else if(entry.getValue()!=null){
                listener.notifyError(entry.getValue());
            }
        }
    }

    private ICalcModel model;
    private ICalcModelListener listener;
    private String result;
    private Exception exc;

    public CalcModelAsync(ICalcModel model){
        this.model=model;
    }

    public void setListener(ICalcModelAsyncListener listener) {
        this.listener=listener;
    }

    @Override
    public void addNumber(final String number) {
        new ModelTask(){
            @Override
            protected Entry<String,Exception> doInBackground(Void... voids) {
                model.addNumber(number);
                return super.doInBackground(voids);
            }
        }.execute();
    }

    @Override
    public void replaceNumber(final String number) {
        new ModelTask(){
            @Override
            protected Entry<String,Exception> doInBackground(Void... voids) {
                model.replaceNumber(number);
                return super.doInBackground(voids);
            }
        }.execute();
    }

    @Override
    public void addOperator(final CalcOpTypes.OpType type) {
        new ModelTask(){
            @Override
            protected Entry<String,Exception> doInBackground(Void... voids) {
                model.addOperator(type);
                return super.doInBackground(voids);
            }
        }.execute();
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
