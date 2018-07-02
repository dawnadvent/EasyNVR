package easynvr.easy.com.easynvr.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("Token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
