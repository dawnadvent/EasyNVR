package easynvr.easy.com.easynvr.Activity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Map;

import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.Tool.SharedHelper;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        SharedHelper helper = new SharedHelper(getApplicationContext());
        Map<String, String> map = helper.readIPPort();

        String ip = map.get("ip");
        String port = map.get("port");

        if (ip != null && port != null) {
            Intent it = new Intent(StartActivity.this, MainActivity.class);
            startActivity(it);
        } else {
            Intent it = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(it);
        }
    }

}
