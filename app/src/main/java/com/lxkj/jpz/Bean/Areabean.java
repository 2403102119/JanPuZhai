package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/27 0027.
 * Describe :
 */

public class Areabean extends ResultBean {

    /**
     * totalPage : 1
     * dataList : [{"skuList":[{"skuName1":"紫葡萄酒","skuPrice":"0.01","skuStock":95,"skuName2":"大瓶","skuId":"2aed5de0ca1244eea5ca139d764c7fe2"},{"skuName1":"紫葡萄酒","skuPrice":"5.00","skuStock":100,"skuName2":"小瓶","skuId":"7c3a90ae2b1f40c58a365cdb6430f995"},{"skuName1":"巨峰葡萄酒","skuPrice":"20.00","skuStock":92,"skuName2":"大瓶","skuId":"a4b26a8764bd4ab1b958ff580dc8f6a6"},{"skuName1":"巨峰葡萄酒","skuPrice":"8.00","skuStock":100,"skuName2":"小瓶","skuId":"f7fc8128d1ab4a8a820570dfd61fe429"}],"productid":"d857000b567642e38496cb2fe630e605","price":"0.01","oldPrice":"1.00","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191120135640IqgKTI.jpg","title":"测试佣金商品测试佣金商品测试佣金商品","sales":13}]
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
         * skuList : [{"skuName1":"紫葡萄酒","skuPrice":"0.01","skuStock":95,"skuName2":"大瓶","skuId":"2aed5de0ca1244eea5ca139d764c7fe2"},{"skuName1":"紫葡萄酒","skuPrice":"5.00","skuStock":100,"skuName2":"小瓶","skuId":"7c3a90ae2b1f40c58a365cdb6430f995"},{"skuName1":"巨峰葡萄酒","skuPrice":"20.00","skuStock":92,"skuName2":"大瓶","skuId":"a4b26a8764bd4ab1b958ff580dc8f6a6"},{"skuName1":"巨峰葡萄酒","skuPrice":"8.00","skuStock":100,"skuName2":"小瓶","skuId":"f7fc8128d1ab4a8a820570dfd61fe429"}]
         * productid : d857000b567642e38496cb2fe630e605
         * price : 0.01
         * oldPrice : 1.00
         * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/20191120135640IqgKTI.jpg
         * title : 测试佣金商品测试佣金商品测试佣金商品
         * sales : 13
         */

        private String productid;
        private String price;
        private String oldPrice;
        private String logo;
        private String title;
        private int sales;
        private List<SkuListBean> skuList;

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public List<SkuListBean> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuListBean> skuList) {
            this.skuList = skuList;
        }

        public static class SkuListBean {
            /**
             * skuName1 : 紫葡萄酒
             * skuPrice : 0.01
             * skuStock : 95
             * skuName2 : 大瓶
             * skuId : 2aed5de0ca1244eea5ca139d764c7fe2
             */

            private String skuName1;
            private String skuPrice;
            private int skuStock;
            private String skuName2;
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

            public int getSkuStock() {
                return skuStock;
            }

            public void setSkuStock(int skuStock) {
                this.skuStock = skuStock;
            }

            public String getSkuName2() {
                return skuName2;
            }

            public void setSkuName2(String skuName2) {
                this.skuName2 = skuName2;
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
