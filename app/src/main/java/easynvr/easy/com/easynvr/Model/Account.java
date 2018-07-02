package easynvr.easy.com.easynvr.Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import easynvr.easy.com.easynvr.BR;
import easynvr.easy.com.easynvr.Tool.MD5Util;

public class Account extends BaseObservable {

    private String ip;
    private String port;
    private String userName;
    private String pwd;

    private String token;

    @Bindable
    public String getIp() {
        return ip == null ? "" : ip;
    }

    @Bindable
    public String getPort() {
        return port == null ? "" : port;
    }

    @Bindable
    public String getUserName() {
        return userName == null ? "" : userName;
    }

    @Bindable
    public String getPwd() {
        return pwd == null ? "" : pwd;
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

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String md5Pwd() {
        return MD5Util.md5(this.pwd);
    }
}
