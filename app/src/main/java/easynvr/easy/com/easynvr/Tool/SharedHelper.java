package easynvr.easy.com.easynvr.Tool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import easynvr.easy.com.easynvr.Model.Account;

public class SharedHelper {
    private Context mContext;

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 保存ip和端口
     * */
    public void saveAccount(Account account) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("ip", account.getIp());
        editor.putString("port", account.getPort());
        editor.putString("userName", account.getUserName());
        editor.putString("pwd", account.getPwd());
        editor.putString("token", account.getToken());

        editor.commit();
    }

    public Account readAccount() {
        Map<String, String> data = new HashMap<>();

        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);

        Account account = new Account();
        account.setIp(sp.getString("ip", ""));
        account.setPort(sp.getString("port", ""));
        account.setUserName(sp.getString("userName", ""));
        account.setPwd(sp.getString("pwd", ""));
        account.setToken(sp.getString("token", ""));

        return account;
    }
}
