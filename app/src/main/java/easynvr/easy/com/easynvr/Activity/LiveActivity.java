package easynvr.easy.com.easynvr.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import easynvr.easy.com.easynvr.BuildConfig;
import easynvr.easy.com.easynvr.HTTP.BaseEntity;
import easynvr.easy.com.easynvr.HTTP.BaseObserver;
import easynvr.easy.com.easynvr.HTTP.RetrofitFactory;
import easynvr.easy.com.easynvr.Model.Channel;
import easynvr.easy.com.easynvr.Model.Live;
import easynvr.easy.com.easynvr.NVRApplication;
import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.View.VideoControllerView2;
import easynvr.easy.com.easynvr.databinding.ActivityLiveBinding;
import io.reactivex.Observable;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class LiveActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private static final String TAG = "LiveActivity";
    public static final int REQUEST_WRITE_STORAGE = 111;

    private ActivityLiveBinding mBinding;
    private View mProgress;

    private VideoControllerView2 mediaController;
    private MediaScannerConnection mScanner;

    private GestureDetector detector;

    private Channel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_live);

        setSupportActionBar(mBinding.liveToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 左边的小箭头（注意需要在setSupportActionBar(toolbar)之后才有效果）
        mBinding.liveToolbar.setNavigationIcon(R.mipmap.back);

        Intent intent = getIntent();
        channel = (Channel) intent.getSerializableExtra("channel");
        mBinding.toolbarTv.setText(channel.getName());
        getChannelStream(channel.getChannel());

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);

        if (BuildConfig.DEBUG) {
            IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        }

        mediaController = new VideoControllerView2(this);
        mediaController.setMediaPlayer(mBinding.videoView);
        mBinding.videoView.setMediaController(mediaController);

        Glide.with(this).load(R.mipmap.loading)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mBinding.gitIv);

        Runnable mSpeedCalcTask = new Runnable() {
            private long mReceivedBytes;

            @Override
            public void run() {
                long l = mBinding.videoView.getReceivedBytes();
                long received = l - mReceivedBytes;
                mReceivedBytes = l;
                mBinding.loadingSpeed.setText(String.format("%3.01fKB/s", received * 1.0f / 1024));

                if (findViewById(android.R.id.progress).getVisibility() == View.VISIBLE){
                    mBinding.videoView.postDelayed(this,1000);
                }
            }
        };
        mBinding.videoView.post(mSpeedCalcTask);

        mProgress = findViewById(android.R.id.progress);

        setListener();
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

    // 获取摄像机url
    private void getChannelStream(String channel) {
        Observable<BaseEntity<Live>> observable = RetrofitFactory.getRetrofitService().getChannelStream(channel, "RTMP/HLS");
        observable.compose(compose(this.<BaseEntity<Live>> bindToLifecycle()))
                .subscribe(new BaseObserver<Live>(this, dialog, null) {
                    @Override
                    protected void onHandleSuccess(Live live) {
                        mBinding.videoView.setVideoPath(live.getUrl());
                        mBinding.videoView.start();
                    }
                });
    }

    public void ptzcontrol(String command) {
        Observable<BaseEntity<Object>> observable = RetrofitFactory
                .getRetrofitService()
                .ptzcontrol(channel.getChannel(), command, "continuous", "5", "onvif");

        observable.compose(compose(this.<BaseEntity<Object>> bindToLifecycle()))
                .subscribe(new BaseObserver<Object>(this, null, null) {
                    @Override
                    protected void onHandleSuccess(Object msg) {

                    }
                });
    }

    private void setListener() {
        mBinding.videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int arg1, int arg2) {
                switch (arg1) {
                    case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                        //mTextView.append("\nMEDIA_INFO_VIDEO_TRACK_LAGGING");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        //mTextView.append("\nMEDIA_INFO_VIDEO_RENDERING_START");
                        mProgress.setVisibility(View.GONE);
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //mTextView.append("\nMEDIA_INFO_BUFFERING_START");
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //mTextView.append("\nMEDIA_INFO_BUFFERING_END");
                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                        //mTextView.append("\nMEDIA_INFO_NETWORK_BANDWIDTH: " + arg2);
                        break;
                    case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                        //mTextView.append("\nMEDIA_INFO_BAD_INTERLEAVING");
                        break;
                    case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                        //mTextView.append("\nMEDIA_INFO_NOT_SEEKABLE");
                        break;
                    case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                        //mTextView.append("\nMEDIA_INFO_METADATA_UPDATE");
                        break;
                    case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                        //mTextView.append("\nMEDIA_INFO_UNSUPPORTED_SUBTITLE");
                        break;
                    case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                        //mTextView.append("\nMEDIA_INFO_SUBTITLE_TIMED_OUT");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                        break;
                    case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                        //mTextView.append("\nMEDIA_INFO_AUDIO_RENDERING_START");
                        break;
                }

                return false;
            }
        });

        mBinding.videoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                mBinding.msgTxt.append("\n播放错误");
                mProgress.setVisibility(View.GONE);
                mBinding.videoView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.videoView.reStart();
                    }
                }, 5000);
                return true;
            }
        });

        mBinding.videoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                mProgress.setVisibility(View.GONE);
                Toast.makeText(LiveActivity.this,"播放完成", Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                Log.i(TAG, String.format("\nonPrepared"));
            }

        });

        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mBinding.videoView.isInPlaybackState()) {
                    mBinding.videoView.toggleMediaControlsVisiblity();
                    return true;
                }
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
//                setRequestedOrientation(isLandscape() ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                return true;
            }
        };

        detector = new GestureDetector(this, listener);
        mBinding.videoView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);

                return true;
            }
        });
    }

    public boolean isLandscape() {
        int orientation = getResources().getConfiguration().orientation;
        return orientation == ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        mBinding.videoView.stopPlayback();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mScanner != null) {
            mScanner.disconnect();
            mScanner = null;
        }
        super.onDestroy();
    }

    public void onChangeOritation(View view) {
        if (isLandscape()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


//            mBinding.liveToolbar.setVisibility(View.VISIBLE);


        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


//            mBinding.liveToolbar.setVisibility(View.GONE);

//            int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//            //设置当前窗体为全屏显示
//            getWindow().setFlags(flag, flag);
        }
    }

    public void onChangePlayMode(View view) {
        int mMode = mBinding.videoView.toggleAspectRatio();
    }

    public void onTakePicture(View view) {
        if (mBinding.videoView.isInPlaybackState()){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
            }else{
                doTakePicture();
            }
        }
    }

    private void doTakePicture() {
        File file = new File(NVRApplication.sPicturePath);
        file.mkdirs();
        file = new File(file, "pic_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg");
        final String picture = mBinding.videoView.takePicture(file.getPath());
        if (!TextUtils.isEmpty(picture)){
            Toast.makeText(this,"图片已保存在\"相册/EasyPlayer\"文件夹下",Toast.LENGTH_SHORT).show();
            if (mScanner == null) {
                MediaScannerConnection connection = new MediaScannerConnection(this,
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            public void onMediaScannerConnected() {
                                mScanner.scanFile(picture, "image/jpeg");
                            }

                            public void onScanCompleted(String path1, Uri uri) {

                            }
                        });
                try {
                    connection.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mScanner = connection;
            } else {
                mScanner.scanFile(picture, "image/jpeg");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_WRITE_STORAGE == requestCode){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doTakePicture();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

//        LinearLayout container = mBinding.playerContainer;
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            setNavVisibility(false);
//            // 横屏情况 播放窗口横着排开
//            container.setOrientation(LinearLayout.HORIZONTAL);
//        } else {
//            // 竖屏,取消全屏状态
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            setNavVisibility(true);
//            // 竖屏情况 播放窗口竖着排开
//            container.setOrientation(LinearLayout.VERTICAL);
//        }
    }

//    public void setNavVisibility(boolean visible) {
//        if (!ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this))) {
//            int newVis = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            if (!visible) {
//                newVis |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
//            }
//            // If we are now visible, schedule a timer for us to go invisible.
//            // Set the new desired visibility.
//            getWindow().getDecorView().setSystemUiVisibility(newVis);
//        }
//    }
}
