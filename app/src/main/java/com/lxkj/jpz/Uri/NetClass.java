package com.lxkj.jpz.Uri;

public class NetClass {


    public static final boolean isDeg = true;
    public static final String BASE_URL;
    public static final String Base_File = "http://39.105.12.51/huayishop/api/uploadFiles";//单张图片
    public static final String Base_FileCui = "http://122.114.49.11:8881/JianPZshop/api/uploadFile";//单张图片



    static {
        if (isDeg) {
            BASE_URL = "http://122.114.49.11:8881/JianPZshop/api/service";//测试
        } else {
            BASE_URL = "http://a.caimogu666.com/mushroom/api/service";//正式
        }
    }


}