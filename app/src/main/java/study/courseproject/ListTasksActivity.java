package study.courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import study.courseproject.task1.CalcActivity;

public class ListTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
        processButtons();
    }

    private void processButtons(){
        int i=1;
        for(View item: Util.getViewsByTag(findViewById(android.R.id.content).getRootView(), "menu_button")){
            Button bt=(Button)item;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListTasksActivity.this, CalcActivity.class);
                    startActivity(intent);
                }
            });
            bt.setText(String.format(bt.getText().toString(), i));
            i++;
        }
    }

}
