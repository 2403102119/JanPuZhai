package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/27 0027.
 * Describe :
 */

public class Seachbean extends ResultBean {

    /**
     * totalPage :
     * dataList : [{"productid":"商品id","title":"商品名称","logo":"商品缩略图","oldPrice":"原价","price":"现价","sales":"销量","skuList":[{"skuId":"规格id","skuName1":"规格1","skuName2":"规格2","skuOldPrice":"原价","skuPrice":"现价","skuStock":"库存"}]}]
     */

    private String totalPage;
    private List<DataListBean> dataList;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
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
         * productid : 商品id
         * title : 商品名称
         * logo : 商品缩略图
         * oldPrice : 原价
         * price : 现价
         * sales : 销量
         * skuList : [{"skuId":"规格id","skuName1":"规格1","skuName2":"规格2","skuOldPrice":"原价","skuPrice":"现价","skuStock":"库存"}]
         */

        private String productid;
        private String title;
        private String logo;
        private String oldPrice;
        private String price;
        private String sales;
        private List<SkuListBean> skuList;

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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
             * skuId : 规格id
             * skuName1 : 规格1
             * skuName2 : 规格2
             * skuOldPrice : 原价
             * skuPrice : 现价
             * skuStock : 库存
             */

            private String skuId;
            private String skuName1;
            private String skuName2;
            private String skuOldPrice;
            private String skuPrice;
            private String skuStock;

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }

            public String getSkuName1() {
                return skuName1;
            }

            public void setSkuName1(String skuName1) {
                this.skuName1 = skuName1;
            }

            public String getSkuName2() {
                return skuName2;
            }

            public void setSkuName2(String skuName2) {
                this.skuName2 = skuName2;
            }

            public String getSkuOldPrice() {
                return skuOldPrice;
            }

            public void setSkuOldPrice(String skuOldPrice) {
                this.skuOldPrice = skuOldPrice;
            }

            public String getSkuPrice() {
                return skuPrice;
            }

            public void setSkuPrice(String skuPrice) {
                this.skuPrice = skuPrice;
            }

            public String getSkuStock() {
                return skuStock;
            }

            public void setSkuStock(String skuStock) {
                this.skuStock = skuStock;
            }
        }
    }
}
