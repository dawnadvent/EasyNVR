package easynvr.easy.com.easynvr.HTTP;

import easynvr.easy.com.easynvr.Model.Live;
import easynvr.easy.com.easynvr.Model.ServiceInfo;
import easynvr.easy.com.easynvr.Model.User;
import easynvr.easy.com.easynvr.Model.Video;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("/api/v1/login")
    Observable<BaseEntity<User>> login(@Query("username") String username, @Query("password") String password);

    @GET("/api/v1/getchannels")
    Observable<BaseEntity<Video>> getChannels(@Query("q") String keyword, @Query("start") int start,@Query("limit") int limit);

    @GET("/api/v1/getchannelstream")
    Observable<BaseEntity<Live>> getChannelStream(@Query("Channel") String channel, @Query("Protocol") String potocol);

    @GET("/api/v1/getserverinfo")
    Observable<BaseEntity<ServiceInfo>> getServerInfo();

    @GET("/api/v1/ptzcontrol")
    Observable<BaseEntity<Object>> ptzcontrol(@Query("channel") String channel,        // 通道ID
                                              @Query("command") String command,        // 动作命令:stop停止、up向上移动、down向下移动、left向左移动、right向右移动、zoomin、zoomout、focusin、focusout、aperturein、apertureout
                                              @Query("actiontype") String actiontype,  // 动作类型:continuous或者single
                                              @Query("speed") String speed,            // 动作速度:5
                                              @Query("protocol") String protocol);     // 摄像机接入的协议:onvif

}
