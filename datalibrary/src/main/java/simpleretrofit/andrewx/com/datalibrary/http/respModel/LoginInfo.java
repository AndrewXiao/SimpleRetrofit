package simpleretrofit.andrewx.com.datalibrary.http.respModel;

/**
 * 必须继承 BaseModel !!!
 */

public class LoginInfo extends BaseModel {
    private String authToken;
    private int taskVersion;
    private int startVersion;
    public int getStartVersion() {
        return this.startVersion;
    }
    public void setStartVersion(int startVersion) {
        this.startVersion = startVersion;
    }
    public int getTaskVersion() {
        return this.taskVersion;
    }
    public void setTaskVersion(int taskVersion) {
        this.taskVersion = taskVersion;
    }
    public String getAuthToken() {
        return this.authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
