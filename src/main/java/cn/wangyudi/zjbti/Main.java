package cn.wangyudi.zjbti;

import okhttp3.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * @author abstractprinter
 */
public class Main {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://wx.app.nbpt.edu.cn/yqtb/weixin/yqtb/submitdate=" + sdf.format(new Date()))
                .addHeader("Cookie", "schoolid=6; PHPSESSID=1b774e27244a905e7485b157a736c46a")
                .post(RequestBody.create("dqld=&frks=&dzdq=&jjxhd=&sxqgjx=&jkm=绿码&memo=&geo[longitude]=121.37762451171875&geo[latitude]=28.365718841552734&geo[province]=浙江省&geo[city]=台州市&geo[district]=温岭市&geo[street]=万泉西路"
                        , MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8")))
                .build();
        try {
            System.out.println(Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
