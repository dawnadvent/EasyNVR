package easynvr.easy.com.easynvr.Model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import easynvr.easy.com.easynvr.NVRApplication;
import easynvr.easy.com.easynvr.R;
import easynvr.easy.com.easynvr.Tool.SharedHelper;

public class Channel extends BaseObservable implements IBaseBindingAdapterItem, Serializable {
    @SerializedName("Channel")
    private String channel; // 通道号

    @SerializedName("Name")
    private String name;    //通道名称

    @SerializedName("Online")
    private int online;  // 是否在线 1在线/0离线

    @SerializedName("SnapURL")
    private String snapUrl; // 快照地址 返回为快照的相对网络地址

    @SerializedName("ErrorString")
    private String errorString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getSnapUrl() {
        SharedHelper helper = new SharedHelper(NVRApplication.getContext());
        return helper.getURL() + snapUrl;
    }

    public void setSnapUrl(String snapUrl) {
        this.snapUrl = snapUrl;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_video;
    }
}