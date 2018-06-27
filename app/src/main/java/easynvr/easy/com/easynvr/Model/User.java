package easynvr.easy.com.easynvr.Model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private long id;

    @SerializedName("token")
    private String token;

    @SerializedName("imageUrl")
    private String imageUrl;

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
