package simpleretrofit.andrewx.com.datalibrary.http.client;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import simpleretrofit.andrewx.com.datalibrary.http.respModel.LoginInfo;
import simpleretrofit.andrewx.com.datalibrary.http.respModel.NewAppVerInfo;


public interface EHService {
    @GET("product/getNewVersionInfo.json")
    Call<NewAppVerInfo> newAppVerInfo(@Query("OSType") String OSType, @Query("DevType") String DevType);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("login/login.json")
    Call<LoginInfo> loginInfo(@Body RequestBody loginReq);
}