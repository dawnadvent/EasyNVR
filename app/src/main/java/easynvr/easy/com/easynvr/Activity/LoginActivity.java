package easynvr.easy.com.easynvr.Activity;

import android.os.Bundle;

import com.trello.rxlifecycle2.android.ActivityEvent;

import easynvr.easy.com.easynvr.HTTP.BaseEntity;
import easynvr.easy.com.easynvr.HTTP.BaseObserver;
import easynvr.easy.com.easynvr.HTTP.RetrofitFactory;
import easynvr.easy.com.easynvr.Model.User;
import easynvr.easy.com.easynvr.R;
import io.reactivex.Observable;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
}
