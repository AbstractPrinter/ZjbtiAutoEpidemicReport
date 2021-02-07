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
        String nowDate;
        String phpSessionId = null;
        boolean isLogin = false, isSign = false;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        OkHttpClient okHttpClient = new OkHttpClient();
        while (true) {
            Date date = new Date();
            nowDate = sdf.format(date);
            if (!"23:59".equals(nowDate)) {
                isLogin = false;
            }
            if (!"00:00".equals(nowDate)) {
                isSign = false;
            }
            if ("23:59".equals(nowDate) && !isLogin) {
                // 获取 Cookie
                Request getCookieRequest = new Request.Builder()
                        .url("https://wx.app.nbpt.edu.cn/yqtb/weixin/yqtb/")
                        .build();
                try {
                    Response response = okHttpClient.newCall(getCookieRequest).execute();
                    phpSessionId = Objects.requireNonNull(response.header("Set-Cookie")).split(";")[0];
                    response.close();
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
                    okHttpClient.newCall(loginRequest).execute().close();
                    isLogin = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ("00:00".equals(nowDate) && !isSign) {
                // 签到
                Request signRequest = new Request.Builder()
                        .url("https://wx.app.nbpt.edu.cn/yqtb/weixin/yqtb/submit?date=" + nowDate)
                        .addHeader("Accept", "application/json")
                        .addHeader("Cookie", "schoolid=6; " + phpSessionId)
                        .post(RequestBody.create("dqld=&frks=&dzdq=&jjxhd=&sxqgjx=&jkm=绿码&memo=&geo[longitude]=121.37762451171875&geo[latitude]=28.365718841552734&geo[province]=浙江省&geo[city]=台州市&geo[district]=温岭市&geo[street]=万泉西路"
                                , MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8")))
                        .build();
                try {
                    Response response = okHttpClient.newCall(signRequest).execute();
                    JSONObject jo = JSON.parseObject(Objects.requireNonNull(response.body()).string());
                    response.close();
                    if (!jo.getBoolean("error")) {
                        isSign = true;
                        new SendCloud("oTWTK0iicgxIg-r-wq6BF-Z2_x3I", "c8f57412c7c6ee4104710702cf3e2ccd")
                                .setFromMail("wangyudi007@gmail.com")
                                .setFromName("Wangyudi")
                                .setToMail("1580694189@qq.com")
                                .setSubject("ZJBTI疫情签到")
                                .setHtml("签到成功！<br />" + jo.toJSONString())
                                .build();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
