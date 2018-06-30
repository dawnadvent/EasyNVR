package easynvr.easy.com.easynvr.Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import easynvr.easy.com.easynvr.BR;

public class Account extends BaseObservable {

    private String ip;
    private String port;
    private String userName;
    private String pwd;

    @Bindable
    public String getIp() {
        return ip;
    }

    @Bindable
    public String getPort() {
        return port;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setIp(String ip) {
        this.ip = ip;
        notifyPropertyChanged(BR.ip);
    }

    public void setPort(String port) {
        this.port = port;
        notifyPropertyChanged(BR.port);
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);
    }
}
