package com.lxkj.jpz.Bean;

import java.util.ArrayList;
import java.util.List;

public class OrderCommentBean {


    private String itemId;//商品订单项ID
    private String productId;//商品ID

    public String getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(String commentScore) {
        this.commentScore = commentScore;
    }

    public  String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    private String commentScore;//评分
    private String content;//评价内容
    public String images ;//评价图片
    public List<String> upLoadImages = new ArrayList<>() ;//评价图片

    public List<String> getUpLoadImages() {
        return upLoadImages;
    }

    public void setUpLoadImages(List<String> upLoadImages) {
        this.upLoadImages = upLoadImages;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
