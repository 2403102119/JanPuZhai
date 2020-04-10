package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/25 0025.
 * Describe :
 */

public class Evluationbean extends ResultBean {

    /**
     * totalPage :
     * dataList : [{"title":"xxx","logo":"xxx","content":"xxx","score":"2","images":["xxx","xxx"],"adtime":""}]
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
         * title : xxx
         * logo : xxx
         * content : xxx
         * score : 2
         * images : ["xxx","xxx"]
         * adtime :
         */

        private String title;
        private String logo;
        private String content;
        private String score;
        private String adtime;
        private List<String> images;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getAdtime() {
            return adtime;
        }

        public void setAdtime(String adtime) {
            this.adtime = adtime;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
