package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/26 0026.
 * Describe :
 */

public class Productlistbean extends ResultBean {


    /**
     * totalPage : 1
     * dataList : [{"productid":"b28dd2af5aee4299a64d1ab9af923741","oldPrice":"100.00","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/2019112623045963hesC.jpg","discount":"10.00","title":"RowingB 2017时尚百搭耳钉 YAN1701","sales":2}]
     */

    private int totalPage;
    private List<DataListBean> dataList;

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
         * productid : b28dd2af5aee4299a64d1ab9af923741
         * oldPrice : 100.00
         * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/2019112623045963hesC.jpg
         * discount : 10.00
         * title : RowingB 2017时尚百搭耳钉 YAN1701
         * sales : 2
         */

        private String productid;
        private String oldPrice;
        private String logo;
        private String discount;
        private String title;
        private int sales;

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }
    }
}
