package study.courseproject.task3;

import android.app.Activity;
import android.os.Bundle;

public class JumpObjsActivity extends Activity{
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JumpObjsBackground v=new JumpObjsBackground(getApplicationContext());
        v.setColor(0xFFAAAAAA);
        setContentView(v);
    }
}
