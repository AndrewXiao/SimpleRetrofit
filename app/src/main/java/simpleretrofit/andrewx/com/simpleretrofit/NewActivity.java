package simpleretrofit.andrewx.com.simpleretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import simpleretrofit.andrewx.com.datalibrary.FormatUtil;
import simpleretrofit.andrewx.com.datalibrary.http.client.BaseCallback;
import simpleretrofit.andrewx.com.datalibrary.http.client.RetrofitClient;
import simpleretrofit.andrewx.com.datalibrary.http.postreq.LoginReq;
import simpleretrofit.andrewx.com.datalibrary.http.respModel.LoginInfo;


public class NewActivity extends AppCompatActivity implements View.OnClickListener{
    private Button threadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datalib_activity_new);

        threadBtn = (Button) findViewById(R.id.thread_btn);
        threadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.thread_btn)
            clickThreadButton();
    }

    /**
     * 后台网络请求 示例: (与 GET, POST 一样！)
     * on200Resp() onNon200Resp() onFailure() 无论如何都将是在UI线程中执行！即使是把 retrofitClient 放入子线程中仍是如此！
     * 当需要处理 onNon200Resp，onFailure 的场景时，则 override 此方法
     *
     * 使用场景：
     * 无需更新UI，比如后台同步 syncType = 0 的 数据，当App进入后台或者退出登录时，进行所有表的 syncType = 0 的扫描以及上传；成功后设置本地 syncType = 1
     * 注意在 on200Resp 不要直接操作 IO/DB/文件 等等耗时操作， 因为在UI线程执行。此时应该开启新线程!
     */
    private void clickThreadButton(){
        LoginReq loginReq = new LoginReq("13051892977", FormatUtil.md5("123456"), "phoneID");
        RetrofitClient.post("loginInfo", loginReq, new BaseCallback<LoginInfo>() {
            @Override
            protected void on200Resp(LoginInfo loginInfo){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 设置 本地数据库 syncType = 1;
                        try {
                            Log.i("TAG", "clickThreadButton Thread name = " + Thread.currentThread().getName() + ", thread id = " + Thread.currentThread().getId());

                            Thread.sleep(5000); // 模拟本地数据库耗时
                            Log.i("TAG", "Thread.sleep(5000)");
                        }catch (InterruptedException e){
                            Log.e("TAG", "InterruptedException e = " + e.getMessage());
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 由于此为后台线程，与UI无关，无需更新UI，因此 onDestroy 时不必 cancel call.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
