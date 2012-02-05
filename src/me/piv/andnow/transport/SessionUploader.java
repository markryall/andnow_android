package me.piv.andnow.transport;

import android.util.Log;
import me.piv.andnow.data.Session;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class SessionUploader {
    private String url;

    public SessionUploader(String url) {
        this.url = url;
    }

    public void upload(Session session) {
        try {
            AbstractHttpClient httpClient = new DefaultHttpClient();
            HttpPost postMethod = new HttpPost(url);
            JSONObject parent = new JSONObject();
            parent.put("session", session.toJSON());
            StringEntity entity = new StringEntity(parent.toString(), "utf-8");
            entity.setContentType("application/json");
            postMethod.setEntity(entity);
            HttpResponse response = httpClient.execute(postMethod);
            String responseString = response.toString();
            Log.i("me.piv", responseString);
        } catch (Exception e) {
            Log.e("me.piv","error sending session", e);
        }
    }
}
