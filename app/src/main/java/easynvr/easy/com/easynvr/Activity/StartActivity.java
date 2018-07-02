package easynvr.easy.com.easynvr.Activity;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import easynvr.easy.com.easynvr.Model.Account;
import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.Tool.SharedHelper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class StartActivity extends BaseActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Observable.interval(1, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
//                .compose(this.<BaseEntity<User>>bindToLifecycle())
//                .compose(compose(this.<BaseEntity<User>> bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        next();
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void next() {
        SharedHelper helper = new SharedHelper(getApplicationContext());
        Account account = helper.readAccount();

        if (!account.getToken().equals("")) {
            Intent it = new Intent(StartActivity.this, MainActivity.class);
            startActivity(it);
        } else {
            Intent it = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(it);
        }

        finish();
    }
}
