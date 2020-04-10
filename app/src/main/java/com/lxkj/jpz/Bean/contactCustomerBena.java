package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/4/2 0002.
 * Describe :
 */

public class contactCustomerBena extends ResultBean {

    /**
     * WX : 13966688868
     * phone : 13966688868
     * facebook : 13966688868
     * dataList : [{"address":"河南省郑州市","phone":"1111111111","logo":""}]
     * worktime : 8:00-18:00
     * email : 13966688868@139.com
     * url : http://122.114.49.11:8881/JianPZshop/display/activity?id=f66973317e19420eb0c898bce0528a71
     */

    private String WX;
    private String phone;
    private String facebook;
    private String worktime;
    private String email;
    private String url;
    private List<DataListBean> dataList;

    public String getWX() {
        return WX;
    }

    public void setWX(String WX) {
        this.WX = WX;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean implements Serializable{
        /**
         * address : 河南省郑州市
         * phone : 1111111111
         * logo :
         */

        private String address;
        private String phone;
        private String logo;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
