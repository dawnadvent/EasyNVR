package easynvr.easy.com.easynvr.Model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

public class ServiceInfo extends BaseObservable {

    @SerializedName("Hardware")
    private String hardware;

    @SerializedName("InterfaceVersion")
    private String interfaceVersion;

    @SerializedName("RunningTime")
    private String runningTime;

    @SerializedName("Server")
    private String server;

    @SerializedName("Authorization")
    private String author;

    @SerializedName("ChannelCount")
    private String channelCount;

    @SerializedName("RemainDays")
    private String remainDays;

    @SerializedName("VersionType")
    private String versionType;

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(String interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(String channelCount) {
        this.channelCount = channelCount;
    }

    public String getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(String remainDays) {
        this.remainDays = remainDays;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }
}
