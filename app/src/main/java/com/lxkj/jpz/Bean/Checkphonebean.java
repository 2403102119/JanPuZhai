package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2019/11/20 0020.
 * Describe :验证手机号shifouzhuce
 */

public class Checkphonebean extends ResultBean {

    /**
     * existence : 0不存在 1存在
     */

    private String existence;

    public String getExistence() {
        return existence;
    }

    public void setExistence(String existence) {
        this.existence = existence;
    }
}
