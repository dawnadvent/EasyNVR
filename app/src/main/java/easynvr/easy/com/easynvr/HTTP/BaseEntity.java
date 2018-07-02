package easynvr.easy.com.easynvr.HTTP;

import com.google.gson.annotations.SerializedName;

/**
 * 服务器通用返回数据格式
 */
public class BaseEntity<E> {

    @SerializedName("EasyDarwin")
    private EasyDarwin<E> easyDarwin;

    public EasyDarwin<E> getEasyDarwin() {
        return easyDarwin;
    }

    public void setEasyDarwin(EasyDarwin<E> easyDarwin) {
        this.easyDarwin = easyDarwin;
    }

    /**
     * EasyDarwin
     * */
    public class EasyDarwin<E> {

        @SerializedName("Header")
        private Header header;

        @SerializedName("Body")
        private E body;

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public E getBody() {
            return body;
        }

        public void setBody(E body) {
            this.body = body;
        }

        /**
         * Header
         * */
        public class Header {
            @SerializedName("ErrorNum")
            private int code;

            @SerializedName("ErrorString")
            private String msg;

            /**
             * 返回正确的状态码是200
             * */
            public boolean isSuccess() {
                return code == 200;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }
        }
    }
}
