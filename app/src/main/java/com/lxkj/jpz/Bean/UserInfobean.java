package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2019/11/22 0022.
 * Describe :个人中心
 */

public class UserInfobean extends ResultBean {


    /**
     * dataObject : {"allprofit":"0.00","address":"","identify":"0","balance":"0.00","phone":"17600167028","sex":"","inviteCode":"475385","name":"2403102119@qq.com","icon":"","setPwd":"0","email":"海岸"}
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
         * allprofit : 0.00
         * address :
         * identify : 0
         * balance : 0.00
         * phone : 17600167028
         * sex :
         * inviteCode : 475385
         * name : 2403102119@qq.com
         * icon :
         * setPwd : 0
         * email : 海岸
         */

        private String allprofit;
        private String address;
        private String identify;
        private String balance;
        private String phone;
        private String sex;
        private String inviteCode;
        private String name;
        private String icon;
        private String setPwd;
        private String email;

        public String getAllprofit() {
            return allprofit;
        }

        public void setAllprofit(String allprofit) {
            this.allprofit = allprofit;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIdentify() {
            return identify;
        }

        public void setIdentify(String identify) {
            this.identify = identify;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSetPwd() {
            return setPwd;
        }

        public void setSetPwd(String setPwd) {
            this.setPwd = setPwd;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
