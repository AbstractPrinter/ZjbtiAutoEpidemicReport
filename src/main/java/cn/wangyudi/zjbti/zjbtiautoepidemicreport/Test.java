package cn.wangyudi.zjbti.zjbtiautoepidemicreport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        System.out.println(sdf.format(new Date()));
    }
}
