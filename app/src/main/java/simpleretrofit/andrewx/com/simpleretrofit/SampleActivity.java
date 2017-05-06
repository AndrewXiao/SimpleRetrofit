package simpleretrofit.andrewx.com.simpleretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import retrofit2.Call;
import simpleretrofit.andrewx.com.datalibrary.FormatUtil;
import simpleretrofit.andrewx.com.datalibrary.http.client.BaseCallback;
import simpleretrofit.andrewx.com.datalibrary.http.client.RetrofitClient;
import simpleretrofit.andrewx.com.datalibrary.http.postreq.LoginReq;
import simpleretrofit.andrewx.com.datalibrary.http.respModel.LoginInfo;
import simpleretrofit.andrewx.com.datalibrary.http.respModel.NewAppVerInfo;


public class SampleActivity extends AppCompatActivity implements View.OnClickListener{

    private Button getBtn;
    private Button postBtn;
    private Button next_btn;
    private TextView textView;
    private Call<NewAppVerInfo> newAppVerInfoCall = null;
    private Call<LoginInfo> loginInfoCall = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datalib_activity_sample);

        initView();
    }

    private void initView(){
        getBtn = (Button) findViewById(R.id.get_btn);
        postBtn = (Button) findViewById(R.id.post_btn);
        next_btn = (Button) findViewById(R.id.next_btn);

        getBtn.setOnClickListener(this);
        postBtn.setOnClickListener(this);
        next_btn.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.tv);
    }

    /**
     * lib 中不允许 R.id.xx 放入 switch-case 中，因为lib中R.id.xx 不是 final 的常量
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.get_btn)
            clickGetButton();
        else if(id == R.id.post_btn)
            clickPostButton();
        else if(id == R.id.next_btn)
            startActivity(new Intent(this, NewActivity.class));
    }

    /**
     * GET 请求 示例:
     * on200Resp() onNon200Resp() onFailure() 无论如何都将是在UI线程中执行！即使是把 retrofitClient 放入子线程中仍是如此！
     * 当需要处理 onNon200Resp，onFailure 的场景时，则 override 此方法
     */
    private void clickGetButton(){
        String [] strArray = {"Android", "1"};
        newAppVerInfoCall = RetrofitClient.get("newAppVerInfo", strArray, new BaseCallback<NewAppVerInfo>() {
            @Override
            protected void on200Resp(NewAppVerInfo newAppVerInfo){
                textView.setText("收到GET结果: newAppVerInfo = " + new Gson().toJson(newAppVerInfo));
            }
        });
    }

    /**
     * POST 请求 示例:
     * on200Resp() onNon200Resp() onFailure() 无论如何都将是在UI线程中执行！即使是把 retrofitClient 放入子线程中仍是如此！
     * 当需要处理 onNon200Resp，onFailure 的场景时，则 override 此方法
     */
    private void clickPostButton(){
        LoginReq loginReq = new LoginReq("13051892977", FormatUtil.md5("123456"), "phoneID");

        loginInfoCall = RetrofitClient.post("loginInfo", loginReq, new BaseCallback<LoginInfo>() {
            @Override
            protected void on200Resp(LoginInfo loginInfo){
                textView.setText("收到POST结果: loginInfo = " + new Gson().toJson(loginInfo));
            }
        });
    }

    /**
     * 上面的2个例子均需要回调更新UI，因此，当 Activity onDestroy的时候要 cancel call！
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(newAppVerInfoCall != null)
            newAppVerInfoCall.cancel();

        if(loginInfoCall != null)
            loginInfoCall.cancel();
    }
}
