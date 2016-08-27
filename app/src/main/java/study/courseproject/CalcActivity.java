package study.courseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.HashMap;

public class CalcActivity extends AppCompatActivity implements ICalcView{
    final static String SCROLL_POSITION="scroll_position";
    private ICalcPresenter presenter;
    private TextView textView;
    private boolean needSave;
    private HorizontalScrollView scrollView;

    private HashMap<String, CalcOpTypes.OpType> map;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calc);

        needSave=false;
        textView=(TextView)this.findViewById(R.id.textView);
        scrollView=(HorizontalScrollView)this.findViewById(R.id.scrollView);

        if(savedInstanceState!=null){
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(
                            savedInstanceState.getInt(SCROLL_POSITION),
                            0
                    );
                }
            });
        }

        setTextButtonClick(
                (Button)findViewById(android.R.id.content).getRootView().findViewWithTag("point_button")
        );

        findViewById(R.id.backspace_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackspaceClick();
            }
        });

        processNumberButtons();
        initHashMap();
        processOperatorButtons();

        CalcPresenterSingleton s=CalcPresenterSingleton.getInstance();
        if(s.hasPresenter()){
            presenter=s.getPresenter();
            presenter.setICalcView(this);
        } else {
            ICalcModel model = new CalcModelAsync(new CalcModel());
            presenter = new CalcPresenter(this, model);
            s.setPresenter(presenter);
        }
    }

    public void setTextButtonClick(final Button bt){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        needSave=true;
        outState.putInt(SCROLL_POSITION, scrollView.getScrollX());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(!needSave){
            CalcPresenterSingleton.getInstance().removePresenter();
        }
    }

    @Override
    public String getTextViewText() {
        return textView.getText().toString();
    }

    @Override
    public void setTextViewText(String str, boolean scrollRight) {
        textView.setText(str);
        scroll(scrollRight);
    }

    private void scroll(final boolean scrollRight){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll((scrollRight)?View.FOCUS_RIGHT:View.FOCUS_LEFT);
            }
        });
    }
}
