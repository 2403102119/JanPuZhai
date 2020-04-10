package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Utils.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/28 0028.
 * Describe :
 */

public class Orderdetailbean extends ResultBean {

    /**
     * orderDetail : {"emscode":"","orderItem":[{"itemId":"671050e290594405beca338110261383","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126214958UtuW2u.jpg","productId":"8a03f3389449412095624acce4660883","productSkuName2":"","productSkuName1":"304","productCount":2,"productName":"刚博士弧形复底优雅304材质16cm304材质奶锅 HX116","productPrice":"63.00"}],"receiverName":"海绵","receiveDate":"","remark":"","emsname":"","emsno":"","receiverAddress":"海南省海口市秀英区2202","payType":"","receiverPhone":"17600167028","createdDate":"2019-11-27 17:35:15","orderPrice":"126.00","deliveryDate":"","refundDate":"","status":"0","payDate":""}
     */

    private OrderDetailBean orderDetail;

    public OrderDetailBean getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailBean orderDetail) {
        this.orderDetail = orderDetail;
    }

    public static class OrderDetailBean {
        /**
         * emscode :
         * orderItem : [{"itemId":"671050e290594405beca338110261383","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126214958UtuW2u.jpg","productId":"8a03f3389449412095624acce4660883","productSkuName2":"","productSkuName1":"304","productCount":2,"productName":"刚博士弧形复底优雅304材质16cm304材质奶锅 HX116","productPrice":"63.00"}]
         * receiverName : 海绵
         * receiveDate :
         * remark :
         * emsname :
         * emsno :
         * receiverAddress : 海南省海口市秀英区2202
         * payType :
         * receiverPhone : 17600167028
         * createdDate : 2019-11-27 17:35:15
         * orderPrice : 126.00
         * deliveryDate :
         * refundDate :
         * status : 0
         * payDate :
         */

        private String emscode;
        private String receiverName;
        private String receiveDate;
        private String remark;
        private String emsname;
        private String emsno;
        private String receiverAddress;
        private String payType;
        private String receiverPhone;
        private String createdDate;
        private String orderPrice;
        private String deliveryDate;
        private String refundDate;
        private String status;
        private String payDate;


        private List<OrderItemBean> orderItem;

        public String getEmscode() {
            return emscode;
        }

        public void setEmscode(String emscode) {
            this.emscode = emscode;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiveDate() {
            return receiveDate;
        }

        public void setReceiveDate(String receiveDate) {
            this.receiveDate = receiveDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getEmsname() {
            return emsname;
        }

        public void setEmsname(String emsname) {
            this.emsname = emsname;
        }

        public String getEmsno() {
            return emsno;
        }

        public void setEmsno(String emsno) {
            this.emsno = emsno;
        }

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getRefundDate() {
            return refundDate;
        }

        public void setRefundDate(String refundDate) {
            this.refundDate = refundDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        public List<OrderItemBean> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItemBean> orderItem) {
            this.orderItem = orderItem;
        }

        public static class OrderItemBean {
            /**
             * itemId : 671050e290594405beca338110261383
             * productImage : http://huayihc.oss-cn-beijing.aliyuncs.com/20191126214958UtuW2u.jpg
             * productId : 8a03f3389449412095624acce4660883
             * productSkuName2 :
             * productSkuName1 : 304
             * productCount : 2
             * productName : 刚博士弧形复底优雅304材质16cm304材质奶锅 HX116
             * productPrice : 63.00
             */

            private String itemId;
            private String productImage;
            private String productId;
            private String productSkuName2;
            private String productSkuName1;
            private String productCount;
            private String productName;
            private String productPrice;

            public ArrayList<ImageItem> getEvaluateSelectPath() {
                return evaluateSelectPath;
            }

            public void setEvaluateSelectPath(ArrayList<ImageItem> evaluateSelectPath) {
                this.evaluateSelectPath = evaluateSelectPath;
            }

            public ArrayList<String> getmSelectPath() {
                return mSelectPath;
            }

            public void setmSelectPath(ArrayList<String> mSelectPath) {
                this.mSelectPath = mSelectPath;
            }

            private ArrayList<ImageItem> evaluateSelectPath = new ArrayList<>();//评价图片
            private ArrayList<String> mSelectPath = new ArrayList<>();



            public boolean isIscheck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

            private boolean ischeck;

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

            public String getProductSkuName2() {
                return productSkuName2;
            }

            public void setProductSkuName2(String productSkuName2) {
                this.productSkuName2 = productSkuName2;
            }

            public String getProductSkuName1() {
                return productSkuName1;
            }

            public void setProductSkuName1(String productSkuName1) {
                this.productSkuName1 = productSkuName1;
            }

            public String getProductCount() {
                return productCount;
            }

            public void setProductCount(String productCount) {
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
