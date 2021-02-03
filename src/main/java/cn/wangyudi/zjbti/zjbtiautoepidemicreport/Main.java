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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        OkHttpClient okHttpClient = new OkHttpClient();
        while (true) {
            nowDate = sdf.format(new Date());
            if (!nowDate.equals(lastDate)) {
                Request reportRequest = new Request.Builder()
                        .url("https://wx.app.nbpt.edu.cn/yqtb/weixin/yqtb/submit?date=" + nowDate)
                        .addHeader("Accept", "application/json")
                        .addHeader("Cookie", "schoolid=6; PHPSESSID=80014d899e906ab2663fb3b2397b0bd0")
                        .post(RequestBody.create("dqld=&frks=&dzdq=&jjxhd=&sxqgjx=&jkm=绿码&memo=&geo[longitude]=121.37762451171875&geo[latitude]=28.365718841552734&geo[province]=浙江省&geo[city]=台州市&geo[district]=温岭市&geo[street]=万泉西路"
                                , MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8")))
                        .build();
                try {
                    Response response = okHttpClient.newCall(reportRequest).execute();
                    JSONObject respawnJson = JSON.parseObject(Objects.requireNonNull(response.body()).string());
                    if (!respawnJson.getBoolean("error")) {
                        System.out.println(respawnJson);
                        new SendCloud("AbstractPrinter_test_Krm36X", "e78c32f95d33a0364c67becb0e6f9131")
                                .setFromMail("abstractprinter@outlook.com")
                                .setFromName("AutoSign")
                                .setToMail("1580694189@qq.com")
                                .setSubject("疫情打卡")
                                .setHtml(respawnJson.toJSONString())
                                .build();
                        lastDate = nowDate;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
