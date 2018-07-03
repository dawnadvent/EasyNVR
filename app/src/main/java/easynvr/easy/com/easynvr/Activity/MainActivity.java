package easynvr.easy.com.easynvr.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        binding.mainToolbar.setOnMenuItemClickListener(this);
        binding.mainToolbar.setTitle("视频广场");

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(manager);

//        binding.recyclerView.setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
            break;
            default:
                break;
        }

        return false;
    }
}
