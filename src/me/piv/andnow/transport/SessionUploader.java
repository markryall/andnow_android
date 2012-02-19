package me.piv.andnow.transport;

import android.net.Uri;
import me.piv.andnow.data.Session;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.UUID;

public class SessionUploader {
    private static final String url = "http://andnow.herokuapp.com/sessions.json";
    private String token;

    public static SessionUploader generate() {
        String token = UUID.randomUUID().toString();
        return new SessionUploader(token);
    }

    public SessionUploader(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    
    public Uri registrationUri() {
        Uri uri = Uri.parse("http://andnow.herokuapp.com/user?key="+this.token);
        return uri;
    }

    public boolean upload(Session session) throws Exception {
        if (token == null) {
            return false;
        }
        AbstractHttpClient httpClient = new DefaultHttpClient();
        HttpPost postMethod = new HttpPost(url);
        JSONObject json = new JSONObject();
        JSONObject sessionJson = session.toJSON();
        json.put("api_token", token);
        json.put("session", sessionJson);
        StringEntity entity = new StringEntity(json.toString(), "utf-8");
        entity.setContentType("application/json");
        postMethod.setEntity(entity);
        HttpResponse response = httpClient.execute(postMethod);
        return response.getStatusLine().getStatusCode() == 201;
    }
}