package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2019/12/3 0003.
 * Describe :
 */

public class AuthorizeloginBean extends ResultBean {

    /**
     * uid : 用户id
     * isBind : 是否绑定手机号
     * phone : xxx
     */

    private String uid;
    private String isBind;
    private String phone;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIsBind() {
        return isBind;
    }

    public void setIsBind(String isBind) {
        this.isBind = isBind;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
