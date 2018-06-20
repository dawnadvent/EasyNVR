package easynvr.easy.com.easynvr.Tool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedHelper {
    private Context mContext;

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void saveIPPort(String ip, String port) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("ip", ip);
        editor.putString("port", port);
        editor.commit();
    }

    public Map<String, String> readIPPort() {
        Map<String, String> data = new HashMap<>();

        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("ip", sp.getString("ip", ""));
        data.put("port", sp.getString("port", ""));
        
        return data;
    }
}
