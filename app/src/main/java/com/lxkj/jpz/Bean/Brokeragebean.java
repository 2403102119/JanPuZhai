package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/25 0025.
 * Describe :
 */

public class Brokeragebean extends ResultBean {


    /**
     * balance : 9555.00
     * walletDetail : [{"amount":"16.00","title":"购买商品","type":"1","adtime":"2020-04-11 16:49:12"},{"amount":"110.00","title":"购买商品","type":"1","adtime":"2020-04-11 16:42:13"},{"amount":"100.00","title":"申请批发商订单","type":"1","adtime":"2020-04-11 16:30:58"},{"amount":"100.00","title":"申请批发商订单","type":"1","adtime":"2020-04-11 16:30:58"},{"amount":"100.00","title":"申请批发商订单","type":"1","adtime":"2020-04-11 16:28:38"},{"amount":"9.00","title":"购买商品","type":"1","adtime":"2020-04-11 16:13:28"},{"amount":"5.00","title":"购买商品","type":"1","adtime":"2020-04-11 08:48:52"},{"amount":"5.00","title":"购买商品","type":"1","adtime":"2020-04-11 08:47:37"}]
     * totalPage : 1
     */

    private String balance;
    private int totalPage;
    private List<WalletDetailBean> walletDetail;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<WalletDetailBean> getWalletDetail() {
        return walletDetail;
    }

    public void setWalletDetail(List<WalletDetailBean> walletDetail) {
        this.walletDetail = walletDetail;
    }

    public static class WalletDetailBean {
        /**
         * amount : 16.00
         * title : 购买商品
         * type : 1
         * adtime : 2020-04-11 16:49:12
         */

        private String amount;
        private String title;
        private String type;
        private String adtime;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAdtime() {
            return adtime;
        }

        public void setAdtime(String adtime) {
            this.adtime = adtime;
        }
    }
}
