package study.courseproject;

import android.os.AsyncTask;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class CalcModelAsync implements ICalcModel, ICalcModelListener{

    class ModelTask extends AsyncTask<Void, Void, Entry<String,Boolean>>{
        @Override
        protected Entry<String,Boolean> doInBackground(Void... voids) {
            return new SimpleEntry<>(result,resultSet);
        }

        @Override
        protected  void onPostExecute(Entry<String,Boolean> entry){
            if(entry.getValue()){
                listener.notifyResult(entry.getKey());
            }
        }
    }

    private ICalcModel model;
    private ICalcModelListener listener;
    private String result;
    private boolean resultSet;
    public CalcModelAsync(ICalcModel model){
        resultSet=false;
        this.model=model;
    }

    @Override
    public void setListener(ICalcModelListener listener) {
        this.listener=listener;
        model.setListener(this);
    }

    @Override
    public void addNumber(final String number) {
        new ModelTask(){
            @Override
            protected Entry<String,Boolean> doInBackground(Void... voids) {
                model.addNumber(number);
                return super.doInBackground(voids);
            }
        }.execute();
    }

    @Override
    public void replaceNumber(final String number) {
        new ModelTask(){
            @Override
            protected Entry<String,Boolean> doInBackground(Void... voids) {
                model.replaceNumber(number);
                return super.doInBackground(voids);
            }
        }.execute();
    }

    @Override
    public void addOperator(final CalcOpTypes.OpType type) {
        new ModelTask(){
            @Override
            protected Entry<String,Boolean> doInBackground(Void... voids) {
                model.addOperator(type);
                return super.doInBackground(voids);
            }
        }.execute();
    }

    @Override
    public void notifyResult(String s) {
        resultSet=true;
        result=s;
    }

    @Override
    public void notifyError(Exception exc) {
    }
}
