# SimpleRetrofit
这是一个对Retrofit 2.0 的二次封装，其目的是为了更加简单的使用Retrofit，这里的例子主要以json为格式的GET/POST请求.

# 其具体使用如下:

例如定义了如下接口
```
public interface EHService {
    @GET("product/getNewVersionInfo.json")
    Call<NewAppVerInfo> newAppVerInfo(@Query("OSType") String OSType, @Query("DevType") String DevType);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("login/login.json")
    Call<LoginInfo> loginInfo(@Body RequestBody loginReq);
}
```

那么，在你开发的代码中可以用2行代码发送GET/POST请求：
 
GET 请求 示例:
```
private void clickGetButton(){
    String [] strArray = {"Android", "1"};
    RetrofitClient.get("newAppVerInfo", strArray, new BaseCallback<NewAppVerInfo>() {
        @Override
        protected void on200Resp(NewAppVerInfo newAppVerInfo){
            textView.setText("收到GET结果: newAppVerInfo = " + new Gson().toJson(newAppVerInfo));
        }
    });
}
```

POST 请求 示例:
```
private void clickPostButton(){
    LoginReq loginReq = new LoginReq("13051892977", FormatUtil.md5("123456"), "phoneID");

    RetrofitClient.post("loginInfo", loginReq, new BaseCallback<LoginInfo>() {
        @Override
        protected void on200Resp(LoginInfo loginInfo){
            textView.setText("收到POST结果: loginInfo = " + new Gson().toJson(loginInfo));
        }
    });
}
```

# 更详细的讲解，请见我的博客
http://blog.csdn.net/qingtiantianqing/article/details/71268749
