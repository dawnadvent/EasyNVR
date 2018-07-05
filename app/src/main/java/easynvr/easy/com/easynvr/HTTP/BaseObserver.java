package easynvr.easy.com.easynvr.HTTP;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zyao89.view.zloading.ZLoadingDialog;

import easynvr.easy.com.easynvr.Activity.LoginActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<E> implements Observer<BaseEntity<E>> {
    private static final String TAG = "BaseObserver";

    private Context mContext;
    private ZLoadingDialog mDialog;
    private PullToRefreshLayout mRefreshLayout;

    protected BaseObserver(Context context, ZLoadingDialog dialog, PullToRefreshLayout refreshLayout) {
        this.mContext = context.getApplicationContext();
        mDialog = dialog;
        mRefreshLayout = refreshLayout;
    }

    @Override
    public void onSubscribe(Disposable d) {
        // 不管取消，和生命周期绑定，由RxLifecycle处理
    }

    @Override
    public void onNext(BaseEntity<E> value) {
        if (value.getEasyDarwin().getHeader().isSuccess()) {
            E e = value.getEasyDarwin().getBody();
            onHandleSuccess(e);
        } else {
            onHandlerError(value.getEasyDarwin().getHeader().getMsg(), value.getEasyDarwin().getHeader().getCode());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError:" + e.toString());
        onHandlerError(e.getMessage(), -1);
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }

    protected abstract void onHandleSuccess(E e);

    /**
     * 对通用问题的统一拦截处理
     * */
    protected void onHandlerError(String msg, int code) {
        mDialog.cancel();

        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadMore();
            mRefreshLayout.finishRefresh();
        }

        switch (code) {
            case 401:
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                break;
        }

        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
