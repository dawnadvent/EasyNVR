package easynvr.easy.com.easynvr.View;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

import easynvr.easy.com.easynvr.R;
import tv.danmaku.ijk.media.widget.media.IMediaController;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

public class VideoControllerView2 extends FrameLayout implements IMediaController, View.OnTouchListener {
    private static final String TAG = "VideoControllerView2";

    private static final int sDefaultTimeout = 10000;

    private MediaController.MediaPlayerControl mPlayer;
    private Context mContext;
    private View mRoot;
    private View mAnchor;

    private boolean mShowing;
    private boolean isAudio;

    private ImageButton fullBtn;
    private ImageButton voiceBtn;

    public VideoControllerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRoot = null;
        mContext = context;
    }

    public VideoControllerView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VideoControllerView2(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        if (mRoot != null)
            initControllerView(mRoot);
    }

    /**
     * Создает вьюху которая будет находится поверх вашего VideoView или другого контролла
     */
    protected View makeControllerView() {
        LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflate.inflate(R.layout.media_controller2, null);

        initControllerView(mRoot);

        return mRoot;
    }

    private void initControllerView(View v) {
        fullBtn = (ImageButton) v.findViewById(R.id.full_btn);
        fullBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer == null) {
                    return;
                }

                if (VideoControllerView2.this.mPlayer instanceof VideoControllerView2.FullscreenableMediaPlayerControl) {
                    VideoControllerView2.FullscreenableMediaPlayerControl mPlayer = (VideoControllerView2.FullscreenableMediaPlayerControl) VideoControllerView2.this.mPlayer;
                    mPlayer.toggleFullScreen();

                    if (mPlayer.isFullScreen()) {
                        fullBtn.setImageResource(R.mipmap.full);
                    } else {
                        fullBtn.setImageResource(R.mipmap.exit);
                    }
                }
            }
        });

        voiceBtn = (ImageButton)v.findViewById(R.id.voice_btn);
        voiceBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer == null) {
                    return;
                }

                if (VideoControllerView2.this.mPlayer instanceof VideoControllerView2.FullscreenableMediaPlayerControl) {
                    VideoControllerView2.FullscreenableMediaPlayerControl mPlayer = (VideoControllerView2.FullscreenableMediaPlayerControl) VideoControllerView2.this.mPlayer;

                    if (isAudio) {
                        isAudio = false;
                        mPlayer.closeAudio();
                        voiceBtn.setImageResource(R.mipmap.mute);
                    } else {
                        isAudio = true;
                        mPlayer.openAudio();
                        voiceBtn.setImageResource(R.mipmap.voice);
                    }
                }
            }
        });

        v.findViewById(R.id.left_btn).setOnTouchListener(this);
        v.findViewById(R.id.right_btn).setOnTouchListener(this);
        v.findViewById(R.id.up_btn).setOnTouchListener(this);
        v.findViewById(R.id.down_btn).setOnTouchListener(this);
        v.findViewById(R.id.far_btn).setOnTouchListener(this);
        v.findViewById(R.id.nearly_btn).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP://松开事件发生后执行代码的区域
                if (VideoControllerView2.this.mPlayer instanceof VideoControllerView2.FullscreenableMediaPlayerControl) {
                    VideoControllerView2.FullscreenableMediaPlayerControl mPlayer = (VideoControllerView2.FullscreenableMediaPlayerControl) VideoControllerView2.this.mPlayer;
                    mPlayer.ptzcontrol("stop");
                }
                break;
            case MotionEvent.ACTION_DOWN://按住事件发生后执行代码的区域
                if (VideoControllerView2.this.mPlayer instanceof VideoControllerView2.FullscreenableMediaPlayerControl) {
                    VideoControllerView2.FullscreenableMediaPlayerControl mPlayer = (VideoControllerView2.FullscreenableMediaPlayerControl) VideoControllerView2.this.mPlayer;

                    switch (v.getId()) {
                        case R.id.left_btn:
                            mPlayer.ptzcontrol("left");
                            break;
                        case R.id.right_btn:
                            mPlayer.ptzcontrol("right");
                            break;
                        case R.id.up_btn:
                            mPlayer.ptzcontrol("up");
                            break;
                        case R.id.down_btn:
                            mPlayer.ptzcontrol("down");
                            break;
                        case R.id.far_btn:
                            mPlayer.ptzcontrol("zoomin");
                            break;
                        case R.id.nearly_btn:
                            mPlayer.ptzcontrol("zoomout");
                            break;
                        default:
                            break;
                    }

                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void hide() {
        if (mAnchor == null) {
            return;
        }

        try {
            if (mAnchor instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) mAnchor;
                vg.removeView(this);
            }
        } catch (IllegalArgumentException ex) {
            Log.w("MediaController", "already removed");
        }

        mShowing = false;
    }

    @Override
    public boolean isShowing() {
        return mShowing;
    }

    @Override
    public void setAnchorView(View view) {
        mAnchor = view;

        removeAllViews();
        View v = makeControllerView();

        RelativeLayout.LayoutParams frameParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        frameParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(v, frameParams);
    }

    @Override
    public void setMediaPlayer(MediaController.MediaPlayerControl player) {
        mPlayer = player;
    }

    @Override
    public void show() {
        show(sDefaultTimeout);
    }

    @Override
    public void showOnce(View view) {

    }

    @Override
    public void show(int timeout) {
        if (!mShowing && mAnchor != null) {
            RelativeLayout.LayoutParams frameParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            frameParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

            if (mAnchor instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) mAnchor;
                vg.addView(this, frameParams);
            }
            mShowing = true;
            isAudio = true;
        }
    }


    public interface FullscreenableMediaPlayerControl extends MediaController.MediaPlayerControl {
        boolean isFullScreen();
        void toggleFullScreen();

        void closeAudio();
        void openAudio();

        void ptzcontrol(String command);

        boolean speedCtrlEnable();
        boolean recordEnable();
        boolean isRecording();
        void toggleRecord();

        float getSpeed();
        void setSpeed(float speed);

        void takePicture();
        void toggleMode();

        boolean isCompleted();
    }
}

