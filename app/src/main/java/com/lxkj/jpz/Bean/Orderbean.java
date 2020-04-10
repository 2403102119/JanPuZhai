package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/27 0027.
 * Describe :
 */

public class Orderbean extends ResultBean {

    /**
     * totalPage : 1
     * dataList : [{"fright":"0.00","emscode":"","orderid":"2020040814430001","orderItem":[{"itemId":"212605633da04d478f024bdbc920ad9b","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/2019112623045963hesC.jpg","productId":"b28dd2af5aee4299a64d1ab9af923741","productSkuName1":"红色","productCount":10,"productName":"RowingB 2017时尚百搭耳钉 YAN1701","productPrice":"5.00"},{"itemId":"fe85c50586d841df91fa3b5c42251d96","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126225911WgskIw.jpg","productId":"2d05b47fd02b4220b3858aab424e632a","productSkuName1":"金色","productCount":6,"productName":"喻金匠 S925银耳钉鱼遇上猫","productPrice":"10.00"}],"orderPrice":"110.00","ispifa":"1","emsname":"","adtime":"2020-04-08 14:43:21","emsno":"","status":"0"},{"fright":"1.00","emscode":"","orderid":"2020040814410001","orderItem":[{"itemId":"af51550f262f4bc2bfbfadb9efa17675","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/2019112623045963hesC.jpg","productId":"b28dd2af5aee4299a64d1ab9af923741","productSkuName1":"红色","productCount":10,"productName":"RowingB 2017时尚百搭耳钉 YAN1701","productPrice":"5.00"}],"orderPrice":"50.00","ispifa":"0","emsname":"","adtime":"2020-04-08 14:41:27","emsno":"","status":"0"}]
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
         * fright : 0.00
         * emscode :
         * orderid : 2020040814430001
         * orderItem : [{"itemId":"212605633da04d478f024bdbc920ad9b","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/2019112623045963hesC.jpg","productId":"b28dd2af5aee4299a64d1ab9af923741","productSkuName1":"红色","productCount":10,"productName":"RowingB 2017时尚百搭耳钉 YAN1701","productPrice":"5.00"},{"itemId":"fe85c50586d841df91fa3b5c42251d96","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126225911WgskIw.jpg","productId":"2d05b47fd02b4220b3858aab424e632a","productSkuName1":"金色","productCount":6,"productName":"喻金匠 S925银耳钉鱼遇上猫","productPrice":"10.00"}]
         * orderPrice : 110.00
         * ispifa : 1
         * emsname :
         * adtime : 2020-04-08 14:43:21
         * emsno :
         * status : 0
         */

        private String fright;
        private String emscode;
        private String orderid;
        private String orderPrice;
        private String ispifa;
        private String emsname;
        private String adtime;
        private String emsno;
        private String status;
        private List<OrderItemBean> orderItem;

        public String getFright() {
            return fright;
        }

        public void setFright(String fright) {
            this.fright = fright;
        }

        public String getEmscode() {
            return emscode;
        }

        public void setEmscode(String emscode) {
            this.emscode = emscode;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getIspifa() {
            return ispifa;
        }

        public void setIspifa(String ispifa) {
            this.ispifa = ispifa;
        }

        public String getEmsname() {
            return emsname;
        }

        public void setEmsname(String emsname) {
            this.emsname = emsname;
        }

        public String getAdtime() {
            return adtime;
        }

        public void setAdtime(String adtime) {
            this.adtime = adtime;
        }

        public String getEmsno() {
            return emsno;
        }

        public void setEmsno(String emsno) {
            this.emsno = emsno;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<OrderItemBean> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItemBean> orderItem) {
            this.orderItem = orderItem;
        }

        public static class OrderItemBean {
            /**
             * itemId : 212605633da04d478f024bdbc920ad9b
             * productImage : http://huayihc.oss-cn-beijing.aliyuncs.com/2019112623045963hesC.jpg
             * productId : b28dd2af5aee4299a64d1ab9af923741
             * productSkuName1 : 红色
             * productCount : 10
             * productName : RowingB 2017时尚百搭耳钉 YAN1701
             * productPrice : 5.00
             */

            private String itemId;
            private String productImage;
            private String productId;
            private String productSkuName1;
            private int productCount;
            private String productName;
            private String productPrice;

            public String getZongji() {
                return zongji;
            }

            public void setZongji(String zongji) {
                this.zongji = zongji;
            }

            private String zongji;

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductSkuName1() {
                return productSkuName1;
            }

            public void setProductSkuName1(String productSkuName1) {
                this.productSkuName1 = productSkuName1;
            }

            public int getProductCount() {
                return productCount;
            }

            public void setProductCount(int productCount) {
                this.productCount = productCount;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(String productPrice) {
                this.productPrice = productPrice;
            }
        }
    }
}
