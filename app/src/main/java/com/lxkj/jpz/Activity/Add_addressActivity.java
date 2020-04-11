package com.lxkj.jpz.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Jsontwobean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.GetJsonDataUtil;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :添加地址
 */
public class Add_addressActivity extends BaseActivity implements View.OnClickListener{

    private String type,addressid,moren;
    private EditText et_name,et_phone,et_detail;
    private TextView tv_site,tv_login;
    /*关于城市选择器*/
    private static boolean isLoaded = false;
    private List<Jsontwobean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private String tx;
    private static final String TAG = "RegistActivity";
    private String province;
    private String city;
    private String twon;
    private LinearLayout ll_setsite;
    private ImageView im_moren;
    private String isdefault = "0";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        //Toast.makeText(RegisterCui1Activity.this, "开始准备数据", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
//                                initJsonData();
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    //Toast.makeText(SelectAddActivity.this, "解析成功", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    //Toast.makeText(SelectAddActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_add_address);
        setTopTitle(getResources().getString(R.string.Edit_shipping_address));

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        tv_site = findViewById(R.id.tv_site);
        et_detail = findViewById(R.id.et_detail);
        tv_login = findViewById(R.id.tv_login);
        ll_setsite = findViewById(R.id.ll_setsite);
        im_moren = findViewById(R.id.im_moren);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
        tv_site.setOnClickListener(this);
        im_moren.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        if (type.equals("0")){
            ll_setsite.setVisibility(View.VISIBLE);
            et_name.setText(getIntent().getStringExtra("name"));
            et_phone.setText(getIntent().getStringExtra("phone"));
            et_detail.setText(getIntent().getStringExtra("detail"));
            tv_site.setText(getIntent().getStringExtra("address"));
            addressid = getIntent().getStringExtra("addressid");
//            moren = getIntent().getStringExtra("moren");
            if (StringUtil_li.isSpace(getIntent().getStringExtra("moren"))){
                im_moren.setImageResource(R.mipmap.guan);
                isdefault = "0";
            }else {
                if (getIntent().getStringExtra("moren").equals("0")){
                    im_moren.setImageResource(R.mipmap.guan);
                    isdefault = "0";
                }else {
                    im_moren.setImageResource(R.mipmap.kai);
                    isdefault = "1";
                }
            }
        }else {
            ll_setsite.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (StringUtil_li.isSpace(et_name.getText().toString())){
                    showToast(getString(R.string.Please_enter_name));
                    return;
                }
                if (StringUtil_li.isSpace(et_phone.getText().toString())){
                    showToast(R.string.Please_enter_a_contact_number);
                    return;
                }
                if (!StringUtil_li.isPhone(et_phone.getText().toString())){
                    showToast(getString(R.string.Please_enter_the_correct_contact_number));
                    return;
                }
                if (StringUtil_li.isSpace(tv_site.getText().toString())){
                    showToast(getString(R.string.Please_select_the_address));
                    return;
                }
                if (StringUtil_li.isSpace(et_detail.getText().toString())){
                    showToast(getString(R.string.Please_enter_the_detailed_address_such_as_the_number_of_the_house));
                    return;
                }
                if (type.equals("0")){
                    updateAddress(addressid,et_name.getText().toString(),et_phone.getText().toString(),tv_site.getText().toString(),et_detail.getText().toString(),isdefault);
                }else {
                    addAddress(et_name.getText().toString(),et_phone.getText().toString(),tv_site.getText().toString(),et_detail.getText().toString());
                }
                break;
            case R.id.tv_site:
                hintKeyBoard();
                //选择城市
                if (isLoaded) {
                    showPickerView();
                }
                break;
            case R.id.im_moren://设为默认地址
                if (isdefault.equals("0")){
                    im_moren.setImageResource(R.mipmap.kai);
                    isdefault = "1";
                }else {
                    im_moren.setImageResource(R.mipmap.guan);
                    isdefault = "0";
                }
                break;
        }
    }
    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    //修改收货地址
    private void updateAddress(String addressId,String name,String phone,String address,String detail,String isdefault) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "updateAddress");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("addressId", addressId);
        params.put("name",name);
        params.put("phone",phone);
        params.put("address",address);
        params.put("detail",detail);
        params.put("isdefault",isdefault);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //添加收货地址
    private void addAddress(String name,String phone,String address,String detail) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "addAddress");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("name",name);
        params.put("phone",phone);
        params.put("address",address);
        params.put("detail",detail);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "city.min.json");//获取assets目录下的json文件数据
        ArrayList<Jsontwobean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getC().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getC().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                if (null != jsonBean.get(i).getC().get(c).getD()){
                    for (int j = 0; j < jsonBean.get(i).getC().get(c).getD().size(); j++) {
                        city_AreaList.add(jsonBean.get(i).getC().get(c).getD().get(j).getName());
                    }
                }
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
    /*三级联动---选择省市县*/
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";
                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";
                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";
                tx = opt1tx + opt2tx + opt3tx;
                //Toast.makeText(RegisterCui1Activity.this, tx, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onOptionsSelect: 选择的是" + tx + "---" + opt1tx + "---" + opt2tx + "---" + opt3tx);
                province = opt1tx;
                city = opt2tx;
                twon = opt3tx;
                tv_site.setText(tx);
            }
        })
                .setTitleText(getString(R.string.Please_select_the_address))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public ArrayList<Jsontwobean> parseData(String result) {//Gson 解析
        ArrayList<Jsontwobean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                Jsontwobean entity = gson.fromJson(data.optJSONObject(i).toString(), Jsontwobean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}
