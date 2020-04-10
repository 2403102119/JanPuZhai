package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/12/5 0005.
 * Describe :
 */

public class Faqbean extends ResultBean {

    private List<DataListBean> dataList;

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * title : 标题
         * url : 富文本链接
         * content : 内容
         */

        private String title;
        private String url;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
    }
}
