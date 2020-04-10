package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/22 0022.
 * Describe :收货地址
 */

public class Addressbean extends ResultBean {

    /**
     * totalPage : 1
     * dataList : [{"isDefault":"","address":"北京市北京市东城区","phone":"17600167028","name":"海绵","detail":"2202","addressId":"94ae6a096d1e42ceb8b1f4f2cf2f30ec"}]
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
         * isDefault :
         * address : 北京市北京市东城区
         * phone : 17600167028
         * name : 海绵
         * detail : 2202
         * addressId : 94ae6a096d1e42ceb8b1f4f2cf2f30ec
         */

        private String isDefault;
        private String address;
        private String phone;
        private String name;
        private String detail;
        private String addressId;

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }
    }
}
