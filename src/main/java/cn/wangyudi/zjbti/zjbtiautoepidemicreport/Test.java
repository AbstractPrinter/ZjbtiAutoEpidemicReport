package cn.wangyudi.zjbti.zjbtiautoepidemicreport;

import okhttp3.*;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("schoolid", "6")
                .add("name", "王誉迪")
                .add("identity", "06202057")
                .build();
        Request request = new Request.Builder()
                .url("https://wx.app.nbpt.edu.cn/yqtb/bind?schoolid=6")
                .addHeader("Cookie","schoolid=6; PHPSESSID=80014d899e906ab2663fb3b2397b0bd0")
                .post(formBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
