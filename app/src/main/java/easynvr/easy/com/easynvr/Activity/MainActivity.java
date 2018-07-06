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

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;

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
    private VideoAdapter adapter;

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

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(manager);

        // 搜索关键字和页码的默认值
        keyword = "";
        start = 0;

        showHub("查询中");
        getchannels();

        binding.recyclerViewRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                start = 0;
                getchannels();
            }

            @Override
            public void loadMore() {
                start++;
                getchannels();
            }
        });

        binding.activityEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        // 隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        String text = binding.searchEt.getText().toString();
        if (text.equals("") && keyword.equals("")) {
            return;
        }

        showHub("搜索中");

        keyword = text;
        start = 0;
        getchannels();
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
        Observable<BaseEntity<Video>> observable = RetrofitFactory.getRetrofitService().getChannels(keyword, start, limit);
        observable.compose(compose(this.<BaseEntity<Video>> bindToLifecycle()))
                .subscribe(new BaseObserver<Video>(this, dialog, binding.recyclerViewRefresh) {
                    @Override
                    protected void onHandleSuccess(Video video) {
                        binding.activityEmptyView.setVisibility(View.GONE);

                        if (adapter == null) {
                            adapter = new VideoAdapter(MainActivity.this, video.getChannels());
                            binding.recyclerView.setAdapter(adapter);

                            if (video.getChannels().size() == 0) {
                                binding.activityEmptyView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (start == 0) {
                                adapter.setmList(video.getChannels());

                                if (video.getChannels().size() == 0) {
                                    binding.activityEmptyView.setVisibility(View.VISIBLE);
                                }
                            } else {
                                adapter.addList(video.getChannels());
                            }
                        }

                        hideHub();
                        binding.recyclerViewRefresh.finishRefresh();
                        binding.recyclerViewRefresh.finishLoadMore();
                    }
                });
    }
}
