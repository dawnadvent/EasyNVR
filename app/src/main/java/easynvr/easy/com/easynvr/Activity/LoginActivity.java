package easynvr.easy.com.easynvr.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle2.android.ActivityEvent;

import easynvr.easy.com.easynvr.HTTP.BaseEntity;
import easynvr.easy.com.easynvr.HTTP.BaseObserver;
import easynvr.easy.com.easynvr.HTTP.RetrofitFactory;
import easynvr.easy.com.easynvr.Model.User;
import easynvr.easy.com.easynvr.R;
import io.reactivex.Observable;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBindingUtil.setContentView(this, R.layout.activity_login);

        // http://demo.easynvr.com:10800/api/v1/login?username=admin&password=xxx
        // 账号密码：admin    Easydarwin123

    }

    private void login(String userId, String pwd) {
        Observable<BaseEntity<User>> observable = RetrofitFactory.getInstance().login(userId, pwd);

        observable
//                .compose(this.<BaseEntity<User>>bindToLifecycle())
//                .compose(compose(this.<BaseEntity<User>> bindUntilEvent(ActivityEvent.DESTROY)))
                .compose(compose(this.<BaseEntity<User>> bindToLifecycle()))
                .subscribe(new BaseObserver<User>(this) {
                    @Override
                    protected void onHandleSuccess(User user) {
                        // TODO
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no_account_btn:

                break;
            case R.id.account_btn:

                break;
            default:

                break;

        }
    }
}
