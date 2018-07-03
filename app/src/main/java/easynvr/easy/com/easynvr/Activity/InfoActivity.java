package easynvr.easy.com.easynvr.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.databinding.ActivityInfoBinding;

public class InfoActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_info);

        setSupportActionBar(binding.infoToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.infoToolbar.setOnMenuItemClickListener(this);
        // 左边的小箭头（注意需要在setSupportActionBar(toolbar)之后才有效果）
        binding.infoToolbar.setNavigationIcon(R.mipmap.back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    // 返回的功能
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
