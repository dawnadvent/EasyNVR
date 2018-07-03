package easynvr.easy.com.easynvr.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import easynvr.easy.com.easynvr.R;

public class LiveActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2.12. Onvif云台控制

    }

}
