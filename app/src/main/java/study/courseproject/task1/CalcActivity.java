package study.courseproject.task1;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.HashMap;

import study.courseproject.ItemSingleton;
import study.courseproject.R;
import study.courseproject.Util;

public class CalcActivity extends AppCompatActivity implements ICalcView{
    /*название ключа в bundle*/
    private final static String SCROLL_POSITION="scroll_position";
    private ICalcPresenter presenter;
    /*циферблат*/
    private TextView numberTextView;
    /*нужно ли сохранять presenter*/
    private boolean needSave;
    /*для прокрутки циферблата*/
    private HorizontalScrollView scrollView;

    /*соответствия текст кнопки — тип оператора*/
    private HashMap<String, CalcOpType> opTypesMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calc);

        scrollView=(HorizontalScrollView)this.findViewById(R.id.scrollView);
        numberTextView =(TextView)this.findViewById(R.id.textView);
        /*восстановление прокрутки*/
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
    }

    @Override
    public void onStart(){
        super.onStart();
        needSave=false;

        /*установка обработчиков кнопок*/
        setTextButtonClick(
                (Button)findViewById(android.R.id.content).getRootView().findViewWithTag("point_button")
        );

        findViewById(R.id.backspace_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackspaceClick();
            }
        });
        findViewById(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onResetClick();
            }
        });
        processNumberButtons();
        initHashMap();
        processOperatorButtons();

        createParts();
    }

    private void createParts(){
        ItemSingleton<ICalcPresenter> s= ItemSingleton.getInstance(ICalcPresenter.class);
        if(s.hasItem()){
            presenter=s.getItem();
            presenter.setICalcView(this);
        } else {
            CalcModel model=new CalcModel();
            CalcModelAsync asyncModel = new CalcModelAsync(model);
            CalcPresenter presenter=new CalcPresenter(this, asyncModel);
            CalcPresenterAsync asyncPresenter = new CalcPresenterAsync(presenter);
            model.setListener(asyncModel);
            asyncModel.setListener(asyncPresenter);
            this.presenter=asyncPresenter;
            s.setItem(this.presenter);
        }
    }

    private void setTextButtonClick(final Button bt){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onTextButtonClick(bt.getText().toString());
            }
        });
    }

    /*установка соответствий текст кнопки — тип оператора*/
    private void initHashMap(){
        this.opTypesMap =new HashMap<>();
        opTypesMap.put(getString(R.string.operator_plus), CalcOpType.PLUS);
        opTypesMap.put(getString(R.string.operator_minus), CalcOpType.MINUS);
        opTypesMap.put(getString(R.string.operator_mul), CalcOpType.MUL);
        opTypesMap.put(getString(R.string.operator_float_div), CalcOpType.FLOAT_DIV);
        opTypesMap.put(getString(R.string.operator_div), CalcOpType.DIV);
        opTypesMap.put(getString(R.string.operator_mod), CalcOpType.MOD);
        opTypesMap.put(getString(R.string.operator_eq), CalcOpType.EQ);
    }

    //подстановка цифр в текст кнопок и добавление обработчиков
    private void processNumberButtons(){
        int i=1;
        for(View item: Util.getViewsByTag(findViewById(android.R.id.content).getRootView(), "number_button")){
            final Button bt=(Button)item;
            bt.setText(String.format(bt.getText().toString(), i));
            setTextButtonClick(bt);
            i=(i+1)%10;
        }
    }

    //добавление обработчиков
    private void processOperatorButtons(){
        for(View item: Util.getViewsByTag(findViewById(android.R.id.content).getRootView(), "operator_button")){
            if(item instanceof Button) {
                final Button bt = (Button) item;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.onOpButtonClick(opTypesMap.get(bt.getText().toString()));
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
    public void onStop(){
        super.onStop();
        if(!needSave){
            ItemSingleton.getInstance(ICalcPresenter.class).removeItem();
        }
    }

    //показать сообщение о непредвиденной ошибки
    @Override
    public void showError(String str){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str)
                .setTitle(R.string.unexpected_error)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //получить текст циферблата
    @Override
    public String getTextViewText() {
        return numberTextView.getText().toString();
    }

    //установить текст циферблата
    @Override
    public void setTextViewText(String str, boolean scrollRight, boolean error) {
        numberTextView.setText(str);
        if(error){
            TextViewCompat.setTextAppearance(numberTextView, R.style.AppTheme_calc_text_view_err);
        } else {
            TextViewCompat.setTextAppearance(numberTextView, R.style.AppTheme_calc_text_view_normal);
        }
        scroll(scrollRight);
    }

    //установить текст циферблата, используя id ресурса
    @Override
    public void setTextViewText(int strResId, boolean scrollRight, boolean error){
        setTextViewText(getString(strResId), scrollRight, error);
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
