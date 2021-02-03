package cn.wangyudi.zjbti.zjbtiautoepidemicreport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author abstractprinter
 */
public class Main {
    public static void main(String[] args) {
        String lastDate = null;
        String nowDate;
        String phpSessionId = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        OkHttpClient okHttpClient = new OkHttpClient();

        nowDate = sdf.format(new Date());
        // 获取 Cookie
        Request getCookieRequest = new Request.Builder()
                .url("https://wx.app.nbpt.edu.cn/yqtb/weixin/yqtb/")
                .build();
        try {
            phpSessionId = Objects.requireNonNull(okHttpClient.newCall(getCookieRequest).execute().header("Set-Cookie")).split(";")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 登录账号
        FormBody formBody = new FormBody.Builder()
                .add("schoolid", "6")
                .add("name", "王誉迪")
                .add("identity", "06202057")
                .build();
        Request loginRequest = new Request.Builder()
                .url("https://wx.app.nbpt.edu.cn/yqtb/bind?schoolid=6")
                .addHeader("Cookie", "schoolid=6; " + phpSessionId)
                .post(formBody)
                .build();
        try {
            okHttpClient.newCall(loginRequest).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 签到
        Request signRequest = new Request.Builder()
                .url("https://wx.app.nbpt.edu.cn/yqtb/weixin/yqtb/submit?date=" + nowDate)
                .addHeader("Accept", "application/json")
                .addHeader("Cookie", "schoolid=6; " + phpSessionId)
                .post(RequestBody.create("dqld=&frks=&dzdq=&jjxhd=&sxqgjx=&jkm=绿码&memo=&geo[longitude]=121.37762451171875&geo[latitude]=28.365718841552734&geo[province]=浙江省&geo[city]=台州市&geo[district]=温岭市&geo[street]=万泉西路"
                        , MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8")))
                .build();
        try {
            System.out.println(Objects.requireNonNull(okHttpClient.newCall(signRequest).execute().body()).string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
