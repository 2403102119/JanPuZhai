package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/12/2 0002.
 * Describe :
 */

public class Detailsrefoundbean extends ResultBean{

    /**
     * orderDetail : {"reason":"不想买了","refundType":"","amount":"303.00","refundtime":"","orderid":"2019120215500001","orderItem":[{"productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191129012921StMQSQ.jpg","productId":"0399ed98dc124d72bdc7e4e58bedc5e1","productSkuName2":"","productSkuName1":"皇家魅影","productCount":1,"productName":"床上用品大提花贡缎高支高密活性精梳全棉 被单 床单 双枕套","productPrice":"295.00"},{"productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165755UBsqRY.jpg","productId":"b860f43ac8864b3fbbc1ea5cc1cfd989","productSkuName2":"","productSkuName1":"500g","productCount":2,"productName":"小甜瓜","productPrice":"4.18"}],"remark":"","adtime":"2019-12-02 17:34:29","status":"6"}
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
         * reason : 不想买了
         * refundType :
         * amount : 303.00
         * refundtime :
         * orderid : 2019120215500001
         * orderItem : [{"productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191129012921StMQSQ.jpg","productId":"0399ed98dc124d72bdc7e4e58bedc5e1","productSkuName2":"","productSkuName1":"皇家魅影","productCount":1,"productName":"床上用品大提花贡缎高支高密活性精梳全棉 被单 床单 双枕套","productPrice":"295.00"},{"productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165755UBsqRY.jpg","productId":"b860f43ac8864b3fbbc1ea5cc1cfd989","productSkuName2":"","productSkuName1":"500g","productCount":2,"productName":"小甜瓜","productPrice":"4.18"}]
         * remark :
         * adtime : 2019-12-02 17:34:29
         * status : 6
         */

        private String reason;
        private String refundType;
        private String amount;
        private String refundtime;
        private String orderid;
        private String remark;
        private String adtime;
        private String status;
        private List<OrderItemBean> orderItem;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getRefundType() {
            return refundType;
        }

        public void setRefundType(String refundType) {
            this.refundType = refundType;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRefundtime() {
            return refundtime;
        }

        public void setRefundtime(String refundtime) {
            this.refundtime = refundtime;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAdtime() {
            return adtime;
        }

        public void setAdtime(String adtime) {
            this.adtime = adtime;
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
             * productImage : http://huayihc.oss-cn-beijing.aliyuncs.com/20191129012921StMQSQ.jpg
             * productId : 0399ed98dc124d72bdc7e4e58bedc5e1
             * productSkuName2 :
             * productSkuName1 : 皇家魅影
             * productCount : 1
             * productName : 床上用品大提花贡缎高支高密活性精梳全棉 被单 床单 双枕套
             * productPrice : 295.00
             */

            private String productImage;
            private String productId;
            private String productSkuName2;
            private String productSkuName1;
            private int productCount;
            private String productName;
            private String productPrice;

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
