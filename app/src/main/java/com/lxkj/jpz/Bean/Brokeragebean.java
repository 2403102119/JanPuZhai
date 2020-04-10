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
     * totalPage :
     * balance : 100.00
     * walletDetail : [{"title":"xxx","amount":"100.00","type":"0","adtime":"xxx"}]
     */

    private String totalPage;
    private String balance;
    private List<WalletDetailBean> walletDetail;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<WalletDetailBean> getWalletDetail() {
        return walletDetail;
    }

    public void setWalletDetail(List<WalletDetailBean> walletDetail) {
        this.walletDetail = walletDetail;
    }

    public static class WalletDetailBean {
        /**
         * title : xxx
         * amount : 100.00
         * type : 0
         * adtime : xxx
         */

        private String title;
        private String amount;
        private String type;
        private String adtime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
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
