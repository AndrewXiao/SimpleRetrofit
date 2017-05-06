package simpleretrofit.andrewx.com.datalibrary.http.postreq;

public class LoginReq {
    private String emailOrPhone;
    private String password;
    private String phoneID;

    public LoginReq(String emailOrPhone, String password, String phoneID) {
        this.emailOrPhone = emailOrPhone;
        this.password = password;
        this.phoneID = phoneID;
    }

    public String getEmailOrPhone() {
        return emailOrPhone;
    }

    public void setEmailOrPhone(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }
}
