package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2019/12/2 0002.
 * Describe :运费
 */

public class Freightbean extends ResultBean {

    /**
     * amount : 运费
     */

    private String amount;

    public String getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(String minMoney) {
        this.minMoney = minMoney;
    }

    private String minMoney;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
