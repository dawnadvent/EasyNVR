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
}
