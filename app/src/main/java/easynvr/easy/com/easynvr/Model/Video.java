package easynvr.easy.com.easynvr.Model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Video extends BaseObservable {

    @SerializedName("ChannelCount")
    private int channelCount;

    @SerializedName("Channels")
    private List<Channel> channels;

    public int getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(int channelCount) {
        this.channelCount = channelCount;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
