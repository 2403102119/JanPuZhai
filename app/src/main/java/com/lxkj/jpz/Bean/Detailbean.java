package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/26 0026.
 * Describe :
 */

public class Detailbean extends ResultBean {


    /**
     * commentList : [{"userName":"昵称","userIcon":"头像","commentScore":"评分","commentDate":"评价时间","commentContent":"评价内容","commentImages":["1.jpg","2.jpg","3.jpg"]}]
     * productDetail : {"skuList":[{"skuName1":"红色","skuPrice":"10.00","skuStock":100,"skuId":"5230c42ffe52400f81f3769d8c869ba8"}],"oldPrice":"100.00","isCollect":"0","sname1":"颜色","video":"","ispifa":"1","type":"1","productName":"RowingB 2017时尚百搭耳钉 YAN1701","sales":0,"url":"http://122.114.49.11:8881/JianPZshop/display/product?id=b28dd2af5aee4299a64d1ab9af923741","productImages":["http://huayihc.oss-cn-beijing.aliyuncs.com/20191126230459pAE1nU.jpg","http://huayihc.oss-cn-beijing.aliyuncs.com/20191126230459s9LF0v.jpg","http://huayihc.oss-cn-beijing.aliyuncs.com/20191126230459Sl9W48.jpg","http://huayihc.oss-cn-beijing.aliyuncs.com/20191126230459azIwYI.jpg"],"price":"10.00","startbuy":"10"}
     * recommentList : [{"productid":"b28dd2af5aee4299a64d1ab9af923741","oldPrice":"100.00","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/2019112623045963hesC.jpg","title":"RowingB 2017时尚百搭耳钉 YAN1701","sales":0},{"productid":"2d05b47fd02b4220b3858aab424e632a","oldPrice":"120.00","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126225911WgskIw.jpg","title":"喻金匠 S925银耳钉鱼遇上猫","sales":1}]
     */

    private ProductDetailBean productDetail;
    private List<CommentListBean> commentList;
    private List<RecommentListBean> recommentList;

    public ProductDetailBean getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetailBean productDetail) {
        this.productDetail = productDetail;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public List<RecommentListBean> getRecommentList() {
        return recommentList;
    }

    public void setRecommentList(List<RecommentListBean> recommentList) {
        this.recommentList = recommentList;
    }


    public static class ProductDetailBean {
        /**
         * productImages : ["http://huayihc.oss-cn-beijing.aliyuncs.com/201911181619158fbpbK.jpg","http://huayihc.oss-cn-beijing.aliyuncs.com/20191118161915NCQzpd.jpg","http://huayihc.oss-cn-beijing.aliyuncs.com/201911181619152R544K.jpg"]
         * skuList : [{"skuName1":"500g","skuPrice":"1000.00","skuStock":195,"skuName2":"","skuId":"77d342b2943f4d8bafb25fcc97ad44c5"}]
         * price : 17.00
         * oldPrice : 22.00
         * isCollect : 0
         * sname1 : 重量
         * sname2 :
         * productName : 猕猴桃
         * sales : 5
         * url : http://39.105.12.51/huayishop/display/product?id=97e75f9474204982974df5cd0b7ccdf4
         * content : 猕猴桃的质地柔软，口感酸甜。味道被描述为草莓、香蕉、菠萝三者的混合。猕猴桃除含有猕猴桃碱、蛋白水解酶、单宁果胶和糖类等有机物，以及钙、钾、硒、锌、锗等微量元素和人体所需17种氨基酸外，还含有丰富的维生素C、葡萄酸、果糖、柠檬酸、苹果酸、脂肪。
         * info : 猕猴桃（学名：Actinidia chinensis Planch），也称奇异果（奇异果是猕猴桃的一个人工选育品种，因使用广泛而成为了猕猴桃的代称）。中国是猕猴桃的原产地，20世纪早期被引入新西兰。2008年11月6日，在新西兰举行的国际猕猴桃大会上，世界19个国家200多位专家一致认定：中国是猕猴桃的原产地，世界猕猴桃原产地在湖北宜昌市夷陵区雾渡河镇。
         果形一般为椭圆状，早期外观呈黄褐色，成熟后呈红褐色，表皮覆盖浓密绒毛，果肉可食用，其内是呈亮绿色的果肉和一排黑色或者红色的种子。因猕猴喜食，故名猕猴桃；亦有说法是因为果皮覆毛，貌似猕猴而得名，是一种品质鲜嫩，营养丰富，风味鲜美的水果。
         */

        private String price;
        private String oldPrice;
        private String isCollect;
        private String sname1;
        private String sname2;
        private String productName;
        private int sales;
        private String url;
        private String content;
        private String info;

        public String getStartbuy() {
            return startbuy;
        }

        public void setStartbuy(String startbuy) {
            this.startbuy = startbuy;
        }

        private String startbuy;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String type;

        public String getIspifa() {
            return ispifa;
        }

        public void setIspifa(String ispifa) {
            this.ispifa = ispifa;
        }

        private String ispifa;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        private String video;
        private List<String> productImages;
        private List<SkuListBean> skuList;

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

        public String getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(String isCollect) {
            this.isCollect = isCollect;
        }

        public String getSname1() {
            return sname1;
        }

        public void setSname1(String sname1) {
            this.sname1 = sname1;
        }

        public String getSname2() {
            return sname2;
        }

        public void setSname2(String sname2) {
            this.sname2 = sname2;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public List<String> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<String> productImages) {
            this.productImages = productImages;
        }

        public List<SkuListBean> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuListBean> skuList) {
            this.skuList = skuList;
        }

        public static class SkuListBean {
            /**
             * skuName1 : 500g
             * skuPrice : 1000.00
             * skuStock : 195
             * skuName2 :
             * skuId : 77d342b2943f4d8bafb25fcc97ad44c5
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

    public static class CommentListBean {
        /**
         * userName : 昵称
         * userIcon : 头像
         * commentScore : 评分
         * commentDate : 评价时间
         * commentContent : 评价内容
         * commentImages : ["1.jpg","2.jpg","3.jpg"]
         */

        private String userName;
        private String userIcon;
        private String commentScore;
        private String commentDate;
        private String commentContent;
        private List<String> commentImages;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getCommentScore() {
            return commentScore;
        }

        public void setCommentScore(String commentScore) {
            this.commentScore = commentScore;
        }

        public String getCommentDate() {
            return commentDate;
        }

        public void setCommentDate(String commentDate) {
            this.commentDate = commentDate;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public List<String> getCommentImages() {
            return commentImages;
        }

        public void setCommentImages(List<String> commentImages) {
            this.commentImages = commentImages;
        }
    }

    public static class RecommentListBean {
        /**
         * productid : b28dd2af5aee4299a64d1ab9af923741
         * oldPrice : 100.00
         * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/2019112623045963hesC.jpg
         * title : RowingB 2017时尚百搭耳钉 YAN1701
         * sales : 0
         */

        private String productid;
        private String oldPrice;
        private String logo;
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
