package easynvr.easy.com.easynvr;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

public class NVRApplication extends Application {
    private static Context context;

    public static final String sPicturePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EasyPlayer";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
