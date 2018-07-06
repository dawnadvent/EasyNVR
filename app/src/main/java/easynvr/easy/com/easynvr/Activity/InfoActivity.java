package easynvr.easy.com.easynvr.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import easynvr.easy.com.easynvr.HTTP.BaseEntity;
import easynvr.easy.com.easynvr.HTTP.BaseObserver;
import easynvr.easy.com.easynvr.HTTP.RetrofitFactory;
import easynvr.easy.com.easynvr.Model.ServiceInfo;
import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.databinding.ActivityInfoBinding;
import io.reactivex.Observable;

public class InfoActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_info);

        setSupportActionBar(binding.infoToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.infoToolbar.setOnMenuItemClickListener(this);
        // 左边的小箭头（注意需要在setSupportActionBar(toolbar)之后才有效果）
        binding.infoToolbar.setNavigationIcon(R.mipmap.back);

        String text = "Copyright © 2012-2018 <font color='#FFFFFF'>www.easynrv.com</font> All rights reversed.";
        binding.copyrightTv.setText(Html.fromHtml(text));

        // 获取EasyNVR服务信息
        Observable<BaseEntity<ServiceInfo>> observable = RetrofitFactory.getRetrofitService().getServerInfo();
        observable.compose(compose(this.<BaseEntity<ServiceInfo>> bindToLifecycle()))
                .subscribe(new BaseObserver<ServiceInfo>(this, dialog, null) {
                    @Override
                    protected void onHandleSuccess(ServiceInfo serviceInfo) {
                        binding.setService(serviceInfo);
                    }
                });
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
