package study.courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import study.courseproject.task1.CalcActivity;
import study.courseproject.task2.SnowmanActivity;
import study.courseproject.task3.JumpObjsActivity;

public class ListTasksActivity extends AppCompatActivity {

    private Class<?> classes[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
        classes=new Class<?>[]{CalcActivity.class, SnowmanActivity.class, JumpObjsActivity.class};
        processButtons();
    }

    private void processButtons(){
        int i=1;
        for(View item: Util.getViewsByTag(findViewById(android.R.id.content).getRootView(), "menu_button")){
            Button bt=(Button)item;
            final int activityId=i-1;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListTasksActivity.this, classes[activityId]);
                    startActivity(intent);
                }
            });
            bt.setText(String.format(bt.getText().toString(), i));
            i++;
        }
    }

}
