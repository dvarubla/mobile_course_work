package study.courseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class CalcActivity extends AppCompatActivity implements ICalcView{
    private ICalcPresenter presenter;
    private TextView textView;

    private HashMap<String, CalcOpTypes.OpType> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        textView=(TextView)this.findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        setTextButtonClick(
                (Button)findViewById(android.R.id.content).getRootView().findViewWithTag("point_button")
        );

        processNumberButtons();
        initHashMap();
        processOperatorButtons();

        ICalcModel model=new CalcModel();
        presenter=new CalcPresenter(this, model);
    }

    public void setTextButtonClick(final Button bt){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setGravity(Gravity.BOTTOM);
                presenter.onTextButtonClick(bt.getText().toString());
            }
        });
    }

    private void initHashMap(){
        this.map=new HashMap<>();
        map.put(getString(R.string.operator_plus), CalcOpTypes.OpType.PLUS);
        map.put(getString(R.string.operator_minus), CalcOpTypes.OpType.MINUS);
        map.put(getString(R.string.operator_mul), CalcOpTypes.OpType.MUL);
        map.put(getString(R.string.operator_float_div), CalcOpTypes.OpType.FLOAT_DIV);
        map.put(getString(R.string.operator_div), CalcOpTypes.OpType.DIV);
        map.put(getString(R.string.operator_mod), CalcOpTypes.OpType.MOD);
        map.put(getString(R.string.operator_eq), CalcOpTypes.OpType.EQ);
    }

    private void processNumberButtons(){
        int i=1;
        for(View item: Util.getViewsByTag(findViewById(android.R.id.content).getRootView(), "number_button")){
            final Button bt=(Button)item;
            bt.setText(String.format(bt.getText().toString(), i));
            setTextButtonClick(bt);
            i=(i+1)%10;
        }
    }

    private void processOperatorButtons(){
        for(View item: Util.getViewsByTag(findViewById(android.R.id.content).getRootView(), "operator_button")){
            if(item instanceof Button) {
                final Button bt = (Button) item;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.onOpButtonClick(map.get(bt.getText().toString()));
                    }
                });
            }
        }
    }

    @Override
    public String getTextViewText() {
        return textView.getText().toString();
    }

    @Override
    public void setTextViewText(String str) {
        textView.setText(str);
    }
}
