package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/4/2 0002.
 * Describe :
 */

public class productCategorybean extends ResultBean {

    private List<DataListBean> dataList;

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * categoryImage : http://huayihc.oss-cn-beijing.aliyuncs.com/20191127014420p9wyvN.png
         * childrenList : [{"childCategoryName":"葡萄","childCategoryId":"15","categoryName":"休闲零食","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911260144479QvVaL.png","categoryId":"9"}]
         * categoryName : 休闲零食
         * categoryId : 9
         */

        private String categoryImage;
        private String categoryName;
        private String categoryId;
        private List<ChildrenListBean> childrenList;

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public List<ChildrenListBean> getChildrenList() {
            return childrenList;
        }

        public void setChildrenList(List<ChildrenListBean> childrenList) {
            this.childrenList = childrenList;
        }

        public static class ChildrenListBean implements Serializable{
            /**
             * childCategoryName : 葡萄
             * childCategoryId : 15
             * categoryName : 休闲零食
             * childCategoryImage : http://huayihc.oss-cn-beijing.aliyuncs.com/201911260144479QvVaL.png
             * categoryId : 9
             */

            private String childCategoryName;
            private String childCategoryId;
            private String categoryName;
            private String childCategoryImage;
            private String categoryId;

            public String getChildCategoryName() {
                return childCategoryName;
            }

            public void setChildCategoryName(String childCategoryName) {
                this.childCategoryName = childCategoryName;
            }

            public String getChildCategoryId() {
                return childCategoryId;
            }

            public void setChildCategoryId(String childCategoryId) {
                this.childCategoryId = childCategoryId;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getChildCategoryImage() {
                return childCategoryImage;
            }

            public void setChildCategoryImage(String childCategoryImage) {
                this.childCategoryImage = childCategoryImage;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }
        }
    }
}
