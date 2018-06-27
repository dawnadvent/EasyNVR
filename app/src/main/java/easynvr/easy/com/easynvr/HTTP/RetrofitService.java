package easynvr.easy.com.easynvr.HTTP;

import java.util.Map;

import easynvr.easy.com.easynvr.Model.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("account/login")
    Observable<BaseEntity<User>> login(@Field("userId") String userId, @Field("password") String password);

    @GET("video/getUrl")
    Observable<BaseEntity<User>> getUrl(@Query("id") long id);

    @FormUrlEncoded
    @POST("user/addVideo")
    Observable<BaseEntity<Boolean>> addVideo(@FieldMap Map<String, Object> map);
}
