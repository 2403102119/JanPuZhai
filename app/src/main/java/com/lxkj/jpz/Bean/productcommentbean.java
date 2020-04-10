package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/26 0026.
 * Describe :
 */

public class productcommentbean extends ResultBean {


    /**
     * badNum : 0
     * totalPage : 1
     * dataList : [{"userName":"昵称","userIcon":"头像","commentScore":"评分","commentDate":"评价时间","commentContent":"评价内容","commentImages":["1.jpg","2.jpg","3.jpg"]}]
     * allNum : 0
     * goodNum : 0
     */

    private int badNum;
    private int totalPage;
    private int allNum;
    private int goodNum;
    private List<DataListBean> dataList;

    public int getBadNum() {
        return badNum;
    }

    public void setBadNum(int badNum) {
        this.badNum = badNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
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
}
