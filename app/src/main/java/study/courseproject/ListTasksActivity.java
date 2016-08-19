package study.courseproject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import java.lang.String;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ListTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
        formatButtonCaptions();
    }

    private void formatButtonCaptions(){
        int[] ids={R.id.task1, R.id.task2, R.id.task3};
        for(int i=0; i<ids.length; i++){
            Button bt=(Button)findViewById(ids[i]);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListTasksActivity.this, CalcActivity.class);
                    startActivity(intent);
                }
            });
            bt.setText(String.format(bt.getText().toString(), i+1));
        }
    }

}
