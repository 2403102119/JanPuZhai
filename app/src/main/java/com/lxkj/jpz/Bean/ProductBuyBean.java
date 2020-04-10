package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2019/12/2 0002.
 * Describe :
 */

public class ProductBuyBean extends ResultBean {

    /**
     * orderId : 订单id
     */

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
