package simpleretrofit.andrewx.com.datalibrary.http.client;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import simpleretrofit.andrewx.com.datalibrary.FormatUtil;
import simpleretrofit.andrewx.com.datalibrary.http.postreq.LoginReq;
import simpleretrofit.andrewx.com.datalibrary.http.respModel.BaseModel;
import simpleretrofit.andrewx.com.datalibrary.http.respModel.LoginInfo;

/**
 * see SampleActivity for detail usage for Retrofit 2.0
 */

public class RetrofitClient {
    private static String authToken = "C81D40D0FA83FDBFE09B8D444215C3A1";
    private static Retrofit retrofit;
    private static EHService service;

    private RetrofitClient(){}

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        RetrofitClient.authToken = authToken;
    }

    private static void initRetrofitClient(){
        if(retrofit == null || service == null){
            synchronized (RetrofitClient.class){
                if(retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl("https://developer.ikangtai.com/ThermometerV2/")
                            .addConverterFactory(GsonConverterFactory.create()) // 必须指定 ConverterFactory
                            .client(getOkHttpClient())
                            .build();
                    service = retrofit.create(EHService.class);
                }
            }
        }
    }

    private static OkHttpClient getOkHttpClient(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(getHttpLoggingInterceptor());

        return httpClientBuilder.build();
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("OkHttpClient","OkHttpMessage:"+message);
            }
        });
        loggingInterceptor.setLevel(level);

        return loggingInterceptor;
    }

    public static EHService requestHttp(){
        initRetrofitClient();
        return service;
    }

    public static <E extends BaseModel> Call<E> get(String getFuncName, String[] paramArray, BaseCallback<E> baseCallback){
        Call<E> typeCall = null;

        try {
            typeCall = (Call<E>) invokeMethod(requestHttp(), getFuncName, paramArray);
            typeCall.enqueue(baseCallback);
        }catch (NoSuchMethodException e){
            Log.e("TAG", "NoSuchMethodException e = " + e.getMessage());
        }catch (IllegalAccessException e){
            Log.e("TAG", "IllegalAccessException e = " + e.getMessage());
        }catch (InvocationTargetException e){
            Log.e("TAG", "InvocationTargetException e = " + e.getMessage());
        }catch (Exception e){
            Log.e("TAG", "Exception e = " + e.getMessage());
        }

        return typeCall;
    }

    /**
     * 反射调用方法
     * @param newObj 实例化的一个对象
     * @param methodName 对象的方法名
     * @param args 参数数组
     * @return 返回值
     * @throws Exception
     */
    public static Object invokeMethod(Object newObj, String methodName, Object[] args)throws Exception {
        Class ownerClass = newObj.getClass();
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(newObj, args);
    }

    public static <T, E extends BaseModel> Call<E> post(String postFuncName, T postBean, BaseCallback<E> baseCallback){
        Call<E> typeCall = null;
        RequestBody requestBody = getRequestBody(postBean);
        try {
            Method EHServiceMethod = EHService.class.getDeclaredMethod(postFuncName, RequestBody.class);
            typeCall = (Call<E>) EHServiceMethod.invoke(requestHttp(), requestBody);
            typeCall.enqueue(baseCallback);
        }catch (NoSuchMethodException e){
            Log.e("TAG", "NoSuchMethodException e = " + e.getMessage());
        }catch (IllegalAccessException e){
            Log.e("TAG", "IllegalAccessException e = " + e.getMessage());
        }catch (InvocationTargetException e){
            Log.e("TAG", "InvocationTargetException e = " + e.getMessage());
        }catch (Exception e){
            Log.e("TAG", "Exception e = " + e.getMessage());
        }

        return typeCall;
    }

    public static <T> RequestBody getRequestBody(T postBean){
        //通过Gson将Bean转化为Json字符串形式
        String reqBeanJson = new Gson().toJson(postBean);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqBeanJson);
    }

    public static void setNewToken(){
        Log.i("TAG", "201 response! setNewToken!");
        LoginReq loginReq = new LoginReq("13051892977", FormatUtil.md5("123456"), "phoneID");
        RequestBody requestBody = RetrofitClient.getRequestBody(loginReq);
        Call<LoginInfo> reLoginCall = RetrofitClient.requestHttp().loginInfo(requestBody);

        try {
            Response<LoginInfo> reLoginResponse = reLoginCall.execute();
            String newToken = reLoginResponse.body().getAuthToken();
            setAuthToken(newToken);
            Log.i("TAG", "201 response! setNewToken! newToken = " + newToken);
        }catch (Exception e){
            Log.e("TAG", "reLoginResponse execute Exception! e = " + e.getMessage());
        }
    }


}
