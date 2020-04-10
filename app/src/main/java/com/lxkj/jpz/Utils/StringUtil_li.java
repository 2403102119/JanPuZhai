package com.lxkj.jpz.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.PhoneNumberUtils;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.lxkj.jpz.App;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil_li {
    private static final String SEP1 = "#";
    private static final String SEP2 = "|";
    private static final String SEP3 = "=";
    /**
     * 判断字符串是否为空
     */
    public static boolean isSpace(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是一个合法的手机号
     */
    public static boolean isPhone(String number) {
        String pattern = "^1(3[0-9]|4[5,7]|5[0-9]|7[0,1,2,3,4,5,6,7,8,9]|8[0-9])\\d{8}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    /**
     * 区号+座机号码+分机号码
     * @param fixedPhone
     * @return
     */
    public static boolean isFixedPhone(String fixedPhone){
        String reg="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        return Pattern.matches(reg, fixedPhone);
    }

    /**
     * 根据身份证号输出年龄
     */
    public static int IdNOToAge(String IdNO) {
        int leh = IdNO.length();
        String dates = "";
        if (leh == 18) {
            int se = Integer.valueOf(IdNO.substring(leh - 1)) % 2;
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            int u = Integer.parseInt(year) - Integer.parseInt(dates);
            return u;
        } else {
            dates = IdNO.substring(6, 8);
            return Integer.parseInt(dates);
        }
    }

    /**
     * 字符串过长自动截取
     */
    public static String cutStr(String str, int length) {
        if (str==null){
            return "";
        }
        if (str.length()<=length){
            return str;
        }
        return str.length()>length?(str.substring(0,2)+"…"+str.substring(str.length()-2)):str;
    }

    /**
     * 调起系统发短信功能
     */
    public static void doSendSMSTo(Activity activity, String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            activity.startActivity(intent);
        }
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     */
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dft.format(endDate);
    }

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     */
    public static String getTimeDifference(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);
            long diff = parse1.getTime() - parse.getTime();
            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
            long hour1 = diff / (60 * 60 * 1000);
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);

            timeString = day + "";
//            timeString = hour1 + "小时" + min1 + "分";
//            System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;
    }

    /**
     * 将秒数转为时分秒
     */
    public static String change(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        if (s < 10) {
            if (d < 10) {
                return "0" + h + ":0" + d + ":0" + s;
            } else {
                if (h < 10) {
                    return "0" + h + ":" + d + ":0" + s;
                } else {
                    return h + ":" + d + ":0" + s;
                }
            }
        } else {
            if (d < 10) {
                return "0" + h + ":0" + d + ":" + s;
            } else {
                if (h < 10) {
                    return "0" + h + ":" + d + ":" + s;
                } else {
                    return h + ":" + d + ":" + s;
                }
            }
        }
    }


    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.NO_WRAP);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }


    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
//byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }

    /**
     * double转String,保留小数点后两位
     * @param num
     * @return
     */
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }


    /**
     * 手机号中间四位替换成*
     * @param phone
     */
    public static String replacePhone(String phone){
        String a = phone.substring(0,3);
        String b = phone.substring(7,11);
        return a+ "****"+ b;
    }

    /*
     * 电桩编号添加空格
     * */
    public static String addSpace(String str){
        StringBuffer DZSb = new StringBuffer();
        for (int i = 0; i < str.length(); i += 2) {
            if ((str.length() - i)%2 == 1 && str.length() - i < 2) {
                DZSb.append(str.substring(i, i + 1));
                DZSb.append(" ");
            }else {
                DZSb.append(str.substring(i, i + 2));
                DZSb.append(" ");
            }
        }
        String DZStr = DZSb.toString().substring(0, DZSb.toString().length() - 1);
        return DZStr;
    }

    /*
     * 电桩编号删除空格
     * */
    public static String deleteSpace(String str){
        if (isSpace(str)){
            return "";
        }

        String[] code1 = str.split(" ");
        String codeStr = "";
        for (int i = 0; i < code1.length; i++) {
            codeStr += code1[i];
        }
        return codeStr;
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //如果显示则隐藏，如果隐藏则显示
    public static void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /*得到Bitmap */
    public static Bitmap decodeFile(File f) {
        try {
            //解码图像大小,对图片进行缩放...防止图片过大导致内存溢出...
            BitmapFactory.Options o = new BitmapFactory.Options();//实例化一个对象...

            o.inJustDecodeBounds = true;//这个就是Options的第一个属性,设置为true的时候，不会完全的对图片进行解码操作,不会为其分配内存，只是获取图片的基本信息...

            BitmapFactory.decodeStream(new FileInputStream(f), null, o); //以码流的形式进行解码....

            /*
             * 下面也就是对图片进行的一个压缩的操作...如果图片过大，最后会根据指定的数值进行缩放...
             * 找到正确的刻度值，它应该是2的幂.
             * 这里我指定了图片的长度和宽度为70个像素...
             *
             * */

            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options(); //这里定义了一个新的对象...获取的还是同一张图片...
            o2.inSampleSize = scale;   //对这张图片设置一个缩放值...inJustDecodeBounds不需要进行设置...
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2); //这样通过这个方法就可以产生一个小的图片资源了...
        } catch (Exception e) {
        }
        return null;
    }

    /*比较2个时间日期的大小*/
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    /**
     * String转换Map
     *
     * @param mapText
     *            :需要转换的字符串
     *            :字符串中的分隔符每一个key与value中的分割
     *            :字符串中每个元素的分割
     * @return Map<?,?>
     */
    public static Map<String, Object> StringToMap(String mapText) {

        if (mapText == null || mapText.equals("")) {
            return null;
        }
        mapText = mapText.substring(1);

        mapText = mapText;

        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split("\\" + SEP2); // 转换为数组
        for (String str : text) {
            String[] keyText = str.split(SEP3); // 转换key与value的数组
            if (keyText.length < 1) {
                continue;
            }
            String key = keyText[0]; // key
            String value = keyText[1]; // value
            if (value.charAt(0) == 'M') {
                Map<?, ?> map1 = StringToMap(value);
                map.put(key, map1);
            } else if (value.charAt(0) == 'L') {
                List<?> list = StringToList(value);
                map.put(key, list);
            } else {
                map.put(key, value);
            }
        }
        return map;
    }


    /**
     * String转换List
     *
     * @param listText
     *            :需要转换的文本
     * @return List<?>
     */
    public static List<Object> StringToList(String listText) {
        if (listText == null || listText.equals("")) {
            return null;
        }
        listText = listText.substring(1);

        listText = listText;

        List<Object> list = new ArrayList<Object>();
        String[] text = listText.split(SEP1);
        for (String str : text) {
            if (str.charAt(0) == 'M') {
                Map<?, ?> map = StringToMap(str);
                list.add(map);
            } else if (str.charAt(0) == 'L') {
                List<?> lists = StringToList(str);
                list.add(lists);
            } else {
                list.add(str);
            }
        }
        return list;
    }

    /**
     * List转换String
     *
     * @param list
     *            :需要转换的List
     * @return String转换后的字符串
     */
    public static String ListToString(List<?> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i) == "") {
                    continue;
                }
                // 如果值是list类型则调用自己
                if (list.get(i) instanceof List) {
                    sb.append(ListToString((List<?>) list.get(i)));
                    sb.append(SEP1);
                } else if (list.get(i) instanceof Map) {
                    sb.append(MapToString((Map<?, ?>) list.get(i)));
                    sb.append(SEP1);
                } else {
                    sb.append(list.get(i));
                    sb.append(SEP1);
                }
            }
        }
        return "L" + sb.toString();
    }
    /**
     * Map转换String
     *
     * @param map
     *            :需要转换的Map
     * @return String转换后的字符串
     */
    public static String MapToString(Map<?, ?> map) {
        StringBuffer sb = new StringBuffer();
        // 遍历map
        for (Object obj : map.keySet()) {
            if (obj == null) {
                continue;
            }
            Object key = obj;
            Object value = map.get(key);
            if (value instanceof List<?>) {
                sb.append(key.toString() + SEP1 + ListToString((List<?>) value));
                sb.append(SEP2);
            } else if (value instanceof Map<?, ?>) {
                sb.append(key.toString() + SEP1
                        + MapToString((Map<?, ?>) value));
                sb.append(SEP2);
            } else {
                sb.append(key.toString() + SEP3 + value.toString());
                sb.append(SEP2);
            }
        }
        return "M" + sb.toString();
    }
    public static String getDateToString(long milSecond) {
        Date date = new Date(milSecond* 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 130, 450, null);
        return bitmap;
    }
    private static File mPhotoFile = null;
    public static void setPhotoFile(File photoFile){
        mPhotoFile = photoFile;
    }

    public static File getPhotoFile(){

        return mPhotoFile;
    }
    public static Bitmap compoundBitmap(Bitmap downBitmap,Bitmap upBitmap)
    {
        Bitmap mBitmap = downBitmap.copy(Bitmap.Config.ARGB_8888, true);
        //如果遇到黑色，则显示downBitmap里面的颜色值，如果不是则显示upBitmap里面的颜色值
        //循环获得bitmap所有像素点
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        //首先保证downBitmap和 upBitmap是一致的高宽大小
        if(mBitmapWidth==upBitmap.getWidth() && mBitmapHeight==upBitmap.getHeight())
        {
            for (int i = 0; i < mBitmapHeight; i++) {
                for (int j = 0; j < mBitmapWidth; j++) {
                    //获得Bitmap 图片中每一个点的color颜色值
                    //将需要填充的颜色值如果不是
                    //在这说明一下 如果color 是全透明 或者全黑 返回值为 0
                    //getPixel()不带透明通道 getPixel32()才带透明部分 所以全透明是0x00000000
                    //而不透明黑色是0xFF000000 如果不计算透明部分就都是0了
                    int color = upBitmap.getPixel(j, i);
                    //将颜色值存在一个数组中 方便后面修改
                    if (color != Color.BLACK) {
                        mBitmap.setPixel(j, i, upBitmap.getPixel(j, i));  //将白色替换成透明色
                    }
                }
            }
        }
//        downBitmap.recycle();
//        upBitmap.recycle();
        return mBitmap;
    }

    /**
     * 保存图片到图库
     * @param bmp
     */
    public static void saveImageToGallery(Bitmap bmp,String bitName ) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "yingtan");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = bitName + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPhotoFile(file);
    }

    /**
     * @param bmp 获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Context context,Bitmap bmp, String picName) {
        saveImageToGallery(bmp,picName);
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }
        }catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MediaStore.Images.Media.insertImage(context.getContentResolver(),bmp,fileName,null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

        Toast.makeText(context,"图片保存成功",Toast.LENGTH_SHORT).show();

    }


    /**
     * 获取手机序列号
     *
     * @return 手机序列号
     */
    @SuppressLint({"NewApi", "MissingPermission"})
    public static String getSerialNumber() {
        String serial = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//9.0+
                serial = Build.getSerial();
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {//8.0+
                serial = Build.SERIAL;
            } else {//8.0-
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ro.serialno");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("e", "读取设备序列号异常：" + e.toString());
        }
        return serial;
    }
    /**
     * 获取随机验证码
     */
    public static String getNum() {
        StringBuilder sb = new StringBuilder();
        //随机生成6位数  发送到聚合
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int a = random.nextInt(10);
            sb.append(a);
        }
        return sb.toString();
    }
    /**
     * 小数点前后大小不一致
     *
     * @param value
     * @return
     */
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.8f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

}
