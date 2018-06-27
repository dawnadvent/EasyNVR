package easynvr.easy.com.easynvr.HTTP;

import com.google.gson.annotations.SerializedName;

/**
 * 服务器通用返回数据格式
 */
public class BaseEntity<E> {

    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private E data;

    /**
     * 返回正确的状态码是200
     * */
    public boolean isSuccess() {
        return code == 200;
    }

    public E getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
