package easynvr.easy.com.easynvr.HTTP;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import easynvr.easy.com.easynvr.Activity.LoginActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
    private static final String TAG = "BaseObserver";

    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {
        // 不管取消，和生命周期绑定，由RxLifecycle处理
    }

    @Override
    public void onNext(BaseEntity<T> value) {
        if (value.isSuccess()) {
            T t = value.getData();
            onHandleSuccess(t);
        } else {
            onHandlerError(value.getMsg(), value.getCode());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError:" + e.toString());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }

    protected abstract void onHandleSuccess(T t);

    /**
     * 对通用问题的统一拦截处理
     * */
    protected void onHandlerError(String msg, int code) {
        switch (code) {
            case 401:
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                break;
        }

        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
