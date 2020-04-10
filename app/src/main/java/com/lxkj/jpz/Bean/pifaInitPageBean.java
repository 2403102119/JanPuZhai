package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2020/4/3 0003.
 * Describe :
 */

public class pifaInitPageBean extends ResultBean {

    /**
     * image : http://122.114.49.11:8881/JianPZshop/userfiles/1/files/distribution/distribution/2020/04/timg.jpg
     * contentUrl : http://122.114.49.11:8881/JianPZshop/display/distribution?id=0
     * money : 100.00
     */

    private String image;
    private String contentUrl;
    private String money;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
