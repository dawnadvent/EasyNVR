package easynvr.easy.com.easynvr.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import easynvr.easy.com.easynvr.HTTP.BaseEntity;
import easynvr.easy.com.easynvr.HTTP.BaseObserver;
import easynvr.easy.com.easynvr.HTTP.RetrofitFactory;
import easynvr.easy.com.easynvr.HTTP.RetrofitService;
import easynvr.easy.com.easynvr.Model.Account;
import easynvr.easy.com.easynvr.Model.User;
import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.Tool.SharedHelper;
import easynvr.easy.com.easynvr.databinding.ActivityLoginBinding;
import io.reactivex.Observable;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        SharedHelper helper = new SharedHelper(getApplicationContext());
        account = helper.readAccount();

        /*
        * 设置默认:http://demo.easynvr.com:10800
        * 账号密码：admin    Easydarwin123
        * */
        if (account.getIp().equals("")) {
            account.setIp("http://demo.easynvr.com");
        }
        if (account.getPort().equals("")) {
            account.setPort("10800");
        }
        if (account.getUserName().equals("")) {
            account.setUserName("admin");
        }
        if (account.getPwd().equals("")) {
            account.setPwd("Easydarwin123");
        }

        helper.saveAccount(account);

        binding.setAccount(account);
        binding.setOnClick(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no_account_btn:
                if (checkInput()) {
                    next();
                }
                break;
            case R.id.account_btn:
                if (checkInput2()) {
                    login();
                }
                break;
            default:

                break;

        }
    }

    private Boolean checkInput() {
        if (account.getIp().equals("")) {
            Toast.makeText(this, "请您输入ip", Toast.LENGTH_SHORT).show();
            return false;
        } else if (account.getPort().equals("")) {
            Toast.makeText(this, "请您输入端口", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Boolean checkInput2() {
        if (checkInput()) {
            if (account.getUserName().equals("")) {
                Toast.makeText(this, "请您输入用户名", Toast.LENGTH_SHORT).show();
                return false;
            } else if (account.getPwd().equals("")) {
                Toast.makeText(this, "请您输入密码", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    private void next() {
        Intent it = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(it);
        finish();
    }

    private void login() {
        // 加载框
        showHub("登录中");

        RetrofitService retrofitService = RetrofitFactory.getInstance().getRetrofitService();
        Observable<BaseEntity<User>> observable = retrofitService.login(account.getUserName(), account.md5Pwd());

        observable
                .compose(compose(this.<BaseEntity<User>> bindToLifecycle()))
                .subscribe(new BaseObserver<User>(this, dialog) {
                    @Override
                    protected void onHandleSuccess(User user) {
                        account.setToken(user.getToken());
                        SharedHelper helper = new SharedHelper(getApplicationContext());
                        helper.saveAccount(account);

                        hideHub();
                        next();
                    }
                });
    }
}
