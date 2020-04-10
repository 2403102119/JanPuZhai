package com.lxkj.qiqihunshe.app.retrofitnet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Message
import android.util.Log
import com.lxkj.jpz.Uri.NetClass
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by Slingge on 2019/3/4
 */
class UpFileUtil(val activity: Activity, val loadFileCallBack: UpLoadFileCallBack) {


    private var list: ArrayList<String>? = null

    private val urlList by lazy { ArrayList<String>() }

    private var count = 0

    fun setListPath(list: ArrayList<String>) {
        this.list = list
        this.list?.let {
            count = 0
            upLoad(it[count])
        }
    }


    private var mOkHttpClient: OkHttpClient? = null
    fun upLoad(path: String) {
        if (count == 0 && urlList.isNotEmpty()) {
            urlList.clear()
        }

        val file = File(path)
        if (mOkHttpClient == null) {
            mOkHttpClient = OkHttpClient()
        }

        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)

        val body = RequestBody.create(MediaType.parse("image/*"), file)

        requestBody.addFormDataPart("file", file.name, body)

        val request = Request.Builder().url(NetClass.Base_FileCui).post(requestBody.build()).build()

        mOkHttpClient!!.newBuilder().readTimeout(60000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(
                object : okhttp3.Callback {
                    override fun onFailure(call: okhttp3.Call, e: IOException) {

                    }

                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                        response.body()?.let {
                            //                        abLog.e("上传图片", it.toString())
                            val obj = JSONObject(it.string())
                            Log.i("123456",obj.toString());
                            if (obj.getString("result") == "0") {
                                if (list == null || list!!.isEmpty()) {
                                    loadFileCallBack.uoLoad(obj.getString("filepath"))
                                } else {
                                    val message = Message()
                                    message.obj = obj.getString("filepath")
                                    handler.sendMessage(message)
                                }
                            } else {

                            }
                        }
                    }
                }
        )
    }


    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            urlList.add(msg.obj.toString())
            count += 1

            if (count <= list!!.size - 1) {
                upLoad(list!![count])
            } else {
                loadFileCallBack.uoLoad(urlList)
                urlList.clear()
            }
        }
    }


}