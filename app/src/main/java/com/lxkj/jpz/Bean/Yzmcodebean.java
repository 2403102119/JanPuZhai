package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2019/11/20 0020.
 * Describe :获取验证码
 */

public class Yzmcodebean extends ResultBean {

    /**
     * code : 验证码
     * phone : 手机号
     */

    private String code;
    private String phone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
