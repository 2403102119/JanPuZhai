package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2020/3/31 0031.
 * Describe :
 */

public class vipInitPageBean extends ResultBean {

    /**
     * image1 : 金牌海报
     * image2 : 银牌海报
     * contentUrl1 : 金牌权益富文本链接
     * contentUrl2 : 银牌权益富文本链接
     * money1 : 金牌价格
     * money2 : 银牌价格
     */

    private String image1;
    private String image2;
    private String contentUrl1;
    private String contentUrl2;
    private String money1;
    private String money2;

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getContentUrl1() {
        return contentUrl1;
    }

    public void setContentUrl1(String contentUrl1) {
        this.contentUrl1 = contentUrl1;
    }

    public String getContentUrl2() {
        return contentUrl2;
    }

    public void setContentUrl2(String contentUrl2) {
        this.contentUrl2 = contentUrl2;
    }

    public String getMoney1() {
        return money1;
    }

    public void setMoney1(String money1) {
        this.money1 = money1;
    }

    public String getMoney2() {
        return money2;
    }

    public void setMoney2(String money2) {
        this.money2 = money2;
    }
}
