package easynvr.easy.com.easynvr.HTTP;

import easynvr.easy.com.easynvr.Model.Live;
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

}
