package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2020/4/7 0007.
 * Describe :
 */

public class setWithdrawal extends ResultBean {

    /**
     * amount : 100
     * rate : 0.6
     */

    private String amount;
    private String rate;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
