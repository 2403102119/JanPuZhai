package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/27 0027.
 * Describe :
 */

public class Cartbean extends ResultBean {

    /**
     * totalPage : 1
     * dataList : [{"image":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126214958UtuW2u.jpg","productId":"8a03f3389449412095624acce4660883","price":"63.00","cartId":"baa5a53ceb654e29a2e1a18aaa4b4c20","count":"1","skuName1":"304","skuName2":"","stock":454,"productName":"刚博士弧形复底优雅304材质16cm304材质奶锅 HX116"}]
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

    public static class DataListBean implements Serializable{
        /**
         * image : http://huayihc.oss-cn-beijing.aliyuncs.com/20191126214958UtuW2u.jpg
         * productId : 8a03f3389449412095624acce4660883
         * price : 63.00
         * cartId : baa5a53ceb654e29a2e1a18aaa4b4c20
         * count : 1
         * skuName1 : 304
         * skuName2 :
         * stock : 454
         * productName : 刚博士弧形复底优雅304材质16cm304材质奶锅 HX116
         */

        private String image;
        private String productId;
        private String price;
        private String cartId;
        private String count;
        private String skuName1;
        private String skuName2;
        private int stock;
        private String productName;

        public String getIspifa() {
            return ispifa;
        }

        public void setIspifa(String ispifa) {
            this.ispifa = ispifa;
        }

        private String ispifa;

        public boolean isIscheck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        private boolean ischeck;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
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

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }
}
