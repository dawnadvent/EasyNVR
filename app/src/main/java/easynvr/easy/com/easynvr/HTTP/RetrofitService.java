package easynvr.easy.com.easynvr.HTTP;

import easynvr.easy.com.easynvr.Model.User;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("/api/v1/login")
    Observable<BaseEntity<User>> login(@Query("username") String username, @Query("password") String password);

//    @GET("video/getUrl")
//    Observable<BaseEntity<User>> getUrl(@Query("id") long id);
//
//    @FormUrlEncoded
//    @POST("user/addVideo")
//    Observable<BaseEntity<Boolean>> addVideo(@FieldMap Map<String, Object> map);
}
