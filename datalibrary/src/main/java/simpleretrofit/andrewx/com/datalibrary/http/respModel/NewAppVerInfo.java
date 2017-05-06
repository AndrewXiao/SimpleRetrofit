package simpleretrofit.andrewx.com.datalibrary.http.respModel;


import java.util.List;

public class NewAppVerInfo extends BaseModel {

    /**
     * updates : [{"update":"1. 新增基础体温列表功能；"},{"update":"2. 优化了登陆和进入主页速度；"},{"update":"3. 新增竖屏曲线；"},{"update":"4. 解决了一些已知问题。"}]
     * versionCode : 46
     * code : 200
     * updateURL : http://www.ikangtai.com/shecareUser/
     * version : 4.2.2
     */

    private int versionCode;
    private String updateURL;
    private String version;
    private List<UpdatesBean> updates;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdateURL() {
        return updateURL;
    }

    public void setUpdateURL(String updateURL) {
        this.updateURL = updateURL;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<UpdatesBean> getUpdates() {
        return updates;
    }

    public void setUpdates(List<UpdatesBean> updates) {
        this.updates = updates;
    }

    public static class UpdatesBean {
        /**
         * update : 1. 新增基础体温列表功能；
         */

        private String update;

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }
    }
}
