package simpleretrofit.andrewx.com.datalibrary.http.respModel;

/**
 * 所有Retrofit返回的Bean，必须继承 BaseModel !!!
 */
public abstract class BaseModel {
    public int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
