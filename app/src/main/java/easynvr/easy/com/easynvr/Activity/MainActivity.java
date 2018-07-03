package easynvr.easy.com.easynvr.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import easynvr.easy.com.easynvr.Adapter.VideoAdapter;
import easynvr.easy.com.easynvr.HTTP.BaseEntity;
import easynvr.easy.com.easynvr.HTTP.BaseObserver;
import easynvr.easy.com.easynvr.HTTP.RetrofitFactory;
import easynvr.easy.com.easynvr.Model.Video;
import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.databinding.ActivityMainBinding;
import io.reactivex.Observable;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private ActivityMainBinding binding;

    private String keyword;
    private int start;
    private static final int limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setOnClick(this);

        setSupportActionBar(binding.mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        binding.mainToolbar.setOnMenuItemClickListener(this);
        binding.mainToolbar.setTitle("视频广场");

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(manager);

        // 搜索关键字和页码的默认值
        keyword = "";
        start = 0;

        showHub("查询中");
        getchannels();
    }

    @Override
    public void onClick(View view) {
        // 隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        if (!binding.searchEt.getText().equals("")) {

            // TODO

            Toast.makeText(this , binding.searchEt.getText(), Toast.LENGTH_SHORT).show();
        }
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

    private void getchannels() {
        Observable<BaseEntity<Video>> observable = RetrofitFactory.getInstance().getRetrofitService().getchannels(keyword, start, limit);

        observable.compose(compose(this.<BaseEntity<Video>> bindToLifecycle()))
                .subscribe(new BaseObserver<Video>(this, dialog) {
                    @Override
                    protected void onHandleSuccess(Video video) {

                        VideoAdapter adapter = new VideoAdapter(MainActivity.this, video.getChannels());
                        binding.recyclerView.setAdapter(adapter);

                        hideHub();
                    }
                });
    }
}
