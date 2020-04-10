package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/23 0023.
 * Describe :
 */

public class Messagebean extends ResultBean {


    /**
     * totalPage :
     * dataList : [{"msgid":"消息id","type":"消息类型 0系统消息 1订单消息","orderid":"订单id","title":"标题","content":"内容","adtime":"添加日期"}]
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
         * msgid : 消息id
         * type : 消息类型 0系统消息 1订单消息
         * orderid : 订单id
         * title : 标题
         * content : 内容
         * adtime : 添加日期
         */

        private String msgid;
        private String type;
        private String orderid;
        private String title;
        private String content;
        private String adtime;

        public String getMsgid() {
            return msgid;
        }

        public void setMsgid(String msgid) {
            this.msgid = msgid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdtime() {
            return adtime;
        }

        public void setAdtime(String adtime) {
            this.adtime = adtime;
        }
    }
}
