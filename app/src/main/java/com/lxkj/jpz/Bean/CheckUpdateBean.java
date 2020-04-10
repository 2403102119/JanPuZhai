package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2019/12/7 0007.
 * Describe :
 */

public class CheckUpdateBean extends ResultBean{


    /**
     * dataObject : {"downurl":"http://39.105.12.51userfiles/1/files/versions/versions/2019/09/1712_jpg_wh1200.jpg","updatecontent":"水岸东方","versionnum":"1.0","num":"2","adtime":"2019-11-12 15:40:56"}
     */

    private DataObjectBean dataObject;

    public DataObjectBean getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObjectBean dataObject) {
        this.dataObject = dataObject;
    }

    public static class DataObjectBean {
        /**
         * downurl : http://39.105.12.51userfiles/1/files/versions/versions/2019/09/1712_jpg_wh1200.jpg
         * updatecontent : 水岸东方
         * versionnum : 1.0
         * num : 2
         * adtime : 2019-11-12 15:40:56
         */

        private String downurl;
        private String updatecontent;
        private String versionnum;
        private String num;
        private String adtime;

        public String getDownurl() {
            return downurl;
        }

        public void setDownurl(String downurl) {
            this.downurl = downurl;
        }

        public String getUpdatecontent() {
            return updatecontent;
        }

        public void setUpdatecontent(String updatecontent) {
            this.updatecontent = updatecontent;
        }

        public String getVersionnum() {
            return versionnum;
        }

        public void setVersionnum(String versionnum) {
            this.versionnum = versionnum;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAdtime() {
            return adtime;
        }

        public void setAdtime(String adtime) {
            this.adtime = adtime;
        }
    }
}
