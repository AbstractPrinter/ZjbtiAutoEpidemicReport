package cn.wangyudi.zjbti.zjbtiautoepidemicreport;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

/**
 * @author abstractprinter
 */
public class SendCloud {
    private final String apiUser;
    private final String apiKey;
    private String fromMail;
    private String fromName;
    private String toMail;
    private String subject;
    private String html;


    public SendCloud(String apiUser, String apiKey) {
        this.apiUser = apiUser;
        this.apiKey = apiKey;
    }

    public SendCloud setFromMail(String fromMail) {
        this.fromMail = fromMail;
        return this;
    }

    public SendCloud setFromName(String fromName) {
        this.fromName = fromName;
        return this;
    }

    public SendCloud setToMail(String toMail) {
        this.toMail = toMail;
        return this;
    }

    public SendCloud setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public SendCloud setHtml(String html) {
        this.html = html;
        return this;
    }

    public void build() {
        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("apiUser", this.apiUser)
                .add("apiKey", this.apiKey)
                .add("to", this.toMail)
                .add("from", this.fromMail)
                .add("fromName", this.fromName)
                .add("subject", this.subject)
                .add("html", this.html)
                .build();
        Request request = new Request.Builder()
                .url("http://api.sendcloud.net/apiv2/mail/send")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Error->" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("Send->" + Objects.requireNonNull(response.body()).string());
                response.close();
            }
        });

    }


}
