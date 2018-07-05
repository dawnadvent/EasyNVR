package easynvr.easy.com.easynvr.Model;

import com.google.gson.annotations.SerializedName;

public class Live {

    @SerializedName("ChannelName")
    private String channelName;

    @SerializedName("URL")
    private String url;

    public String getChannelName() {
        return channelName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
