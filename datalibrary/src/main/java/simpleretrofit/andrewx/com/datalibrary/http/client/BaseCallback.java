package simpleretrofit.andrewx.com.datalibrary.http.client;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simpleretrofit.andrewx.com.datalibrary.http.respModel.BaseModel;

public abstract class BaseCallback<T extends BaseModel> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        int networkRespCode = response.raw().code();
        Log.i("TAG", "BaseCallback onResponse! networkRespCode = " + networkRespCode);
        T bean = response.body();

        if (networkRespCode == 200) { // 200是服务器有合理响应, IP层的HTTP回应
            if(bean.getCode() == 200) on200Resp(bean); // Response 里 json 层 code: 200
            else onNon200Resp(bean);
        }else
            onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e("TAG", "BaseCallback onFailure! call = " + call.request().url().toString());
        onFailure(t);
    }

    /**
     * on200Resp() onNon200Resp() onFailure() 无论如何都将是在UI线程中执行！即使是把 retrofitClient 放入子线程中仍是如此！
     * 当需要处理 onNon200Resp，onFailure 的场景时，则 override 此方法
     *
     */
    protected abstract void on200Resp(T bean);
    void onNon200Resp(T bean){
        Log.e("TAG", "receive non-200 response, code is " + bean.getCode());
    }
    void onFailure(Throwable t){
        Log.e("TAG", "on Failure! Throwable message = " + t.getMessage() + ", stack trace = " + t.getStackTrace());
    }
}
