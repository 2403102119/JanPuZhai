package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/25 0025.
 * Describe :
 */

public class Collectbean extends ResultBean {


    /**
     * totalPage : 1
     * dataList : [{"skuList":[{"skuName1":"8斤","skuPrice":"12.00","stock":997,"skuId":"cb41f8e6b4fc4afeb02a047eff89a736"}],"productid":"b22eecb4b22e4e2db34d7156077087bf","oldPrice":"20.00","startBuy":"","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165413tTJ4K2.jpg","ispifa":"0","title":"青苹果","sales":""}]
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
         * skuList : [{"skuName1":"8斤","skuPrice":"12.00","stock":997,"skuId":"cb41f8e6b4fc4afeb02a047eff89a736"}]
         * productid : b22eecb4b22e4e2db34d7156077087bf
         * oldPrice : 20.00
         * startBuy :
         * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165413tTJ4K2.jpg
         * ispifa : 0
         * title : 青苹果
         * sales :
         */

        private String productid;
        private String oldPrice;
        private String startBuy;
        private String logo;
        private String ispifa;
        private String title;
        private String sales;
        private List<SkuListBean> skuList;

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

        public String getStartBuy() {
            return startBuy;
        }

        public void setStartBuy(String startBuy) {
            this.startBuy = startBuy;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getIspifa() {
            return ispifa;
        }

        public void setIspifa(String ispifa) {
            this.ispifa = ispifa;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public List<SkuListBean> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuListBean> skuList) {
            this.skuList = skuList;
        }

        public static class SkuListBean {
            /**
             * skuName1 : 8斤
             * skuPrice : 12.00
             * stock : 997
             * skuId : cb41f8e6b4fc4afeb02a047eff89a736
             */

            private String skuName1;
            private String skuPrice;
            private int stock;
            private String skuId;

            public String getSkuName1() {
                return skuName1;
            }

            public void setSkuName1(String skuName1) {
                this.skuName1 = skuName1;
            }

            public String getSkuPrice() {
                return skuPrice;
            }

            public void setSkuPrice(String skuPrice) {
                this.skuPrice = skuPrice;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }
        }
    }
}
