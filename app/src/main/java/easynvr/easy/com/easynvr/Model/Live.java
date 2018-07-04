package easynvr.easy.com.easynvr.Model;

import com.google.gson.annotations.SerializedName;

public class Live {

    @SerializedName("ChannelName")
    private int channelName;

    @SerializedName("URL")
    private int url;

    public int getChannelName() {
        return channelName;
    }

    public void setChannelName(int channelName) {
        this.channelName = channelName;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
