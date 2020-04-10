package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/25 0025.
 * Describe :
 */

public class Walletbean extends ResultBean {


    /**
     * balance : 667.90
     * totalPage : 1
     * dataList : [{"amount":"153.60","title":"1级好友下单返佣-二级啦啦啦","type":"0","adtime":"2019-12-03 15:10:37"},{"amount":"303.30","title":"1级好友下单返佣-二级啦啦啦","type":"0","adtime":"2019-12-03 15:01:55"},{"amount":"21.00","title":"1级好友下单返佣-二级啦啦啦","type":"0","adtime":"2019-11-27 14:13:33"},{"amount":"200.00","title":"1级好友礼包返佣-二级啦啦啦","type":"0","adtime":"2019-11-27 14:06:58"}]
     */

    private String balance;
    private int totalPage;
    private List<DataListBean> dataList;

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

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * amount : 153.60
         * title : 1级好友下单返佣-二级啦啦啦
         * type : 0
         * adtime : 2019-12-03 15:10:37
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
