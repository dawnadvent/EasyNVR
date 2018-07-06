package easynvr.easy.com.easynvr.View;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import easynvr.easy.com.easynvr.Activity.LiveActivity;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

public class ProVideoView extends IjkVideoView implements VideoControllerView.FullscreenableMediaPlayerControl {
    private String mRecordPath;
    private int mRecordFileIndex = 0;

    public ProVideoView(Context context) {
        super(context);
    }

    public ProVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ProVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isFullScreen() {
        if (getContext() instanceof LiveActivity){
            LiveActivity pro = (LiveActivity) getContext();
            return pro.isLandscape();
        }
        return false;
    }

    @Override
    public void toggleFullScreen() {
        if (getContext() instanceof LiveActivity){
            LiveActivity pro = (LiveActivity) getContext();
            pro.onChangeOritation(null);
        }
    }

    @Override
    public boolean recordEnable() {
        Uri uri = mUri;
        if (uri == null) return false;
        if (uri.getScheme() == null) return false;
        return !uri.getScheme().equals("file");
    }

    @Override
    public boolean speedCtrlEnable() {
        Uri uri = mUri;
        if (uri == null) return false;
        if (uri.getScheme() == null) return true;
        return uri.getScheme().equals("file");
    }

    @Override
    public boolean isRecording() {
        if (mMediaPlayer == null){
            return false;
        }
        return !TextUtils.isEmpty(mRecordPath);
    }

    @Override
    public void reStart(){
        super.reStart();
        if (mRecordPath != null){
            toggleRecord();
            toggleRecord();
        }
    }

    @Override
    public void toggleRecord() {
        if (getContext() instanceof LiveActivity){
            LiveActivity pro = (LiveActivity) getContext();
            if (ActivityCompat.checkSelfPermission(pro, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(pro, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, LiveActivity.REQUEST_WRITE_STORAGE +1);
                return;
            }
        }

        if (!isRecording()){
            Uri uri = mUri;
            if (uri == null) return;

//            mRecordPath = uri.getLastPathSegment();
//            if (mRecordPath.endsWith(".mp4")){
//                mRecordPath = mRecordPath.substring(0, mRecordPath.lastIndexOf(".mp4"));
//            }
//            mRecordPath += "_" + mRecordFileIndex++ + ".mp4";

            mRecordPath = "record_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp4";
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            try {
                directory.mkdirs();
                startRecord(directory + "/" + mRecordPath, 30);
            }catch (Exception ex){
                ex.printStackTrace();
                mRecordPath = null;
            }
        }else{
            stopRecord();
        }
    }

    @Override
    public float getSpeed() {
        if (mMediaPlayer == null ){
            return 1.0f;
        }
        if (mMediaPlayer instanceof IjkMediaPlayer){
            IjkMediaPlayer player = (IjkMediaPlayer) mMediaPlayer;
            return player.getSpeed();
        }
        return 1.0f;
    }

    @Override
    public void setSpeed(float speed) {
        if (mMediaPlayer == null ){
            return ;
        }
        if (mMediaPlayer instanceof IjkMediaPlayer){
            IjkMediaPlayer player = (IjkMediaPlayer) mMediaPlayer;
            player.setSpeed(speed);
        }
    }

    @Override
    public void takePicture() {
        if (getContext() instanceof LiveActivity){
            LiveActivity pro = (LiveActivity) getContext();
            pro.onTakePicture(null);
        }
    }

    @Override
    public void toggleMode() {
        if (getContext() instanceof LiveActivity){
            LiveActivity pro = (LiveActivity) getContext();
            pro.onChangePlayMode(null);
        }
    }

    @Override
    public boolean isCompleted() {
        if (mMediaPlayer instanceof IjkMediaPlayer){
            IjkMediaPlayer player = (IjkMediaPlayer) mMediaPlayer;
            return player.isCompleted();
        }
        return false;
    }

    public void startRecord(String path, int seconds) {
        if (mMediaPlayer == null){
            return;
        }
        super.startRecord(path,seconds);
        mRecordPath = path;
    }

    public void stopRecord() {
        if (mMediaPlayer == null){
            return;
        }
        super.stopRecord();
        mRecordPath = null;
    }
}
