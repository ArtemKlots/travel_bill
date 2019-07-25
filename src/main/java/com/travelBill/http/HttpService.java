package com.travelBill.http;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HttpService {

    public HttpResponse post(String url, List<NameValuePair> params) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        return httpclient.execute(httppost);
    }
}
