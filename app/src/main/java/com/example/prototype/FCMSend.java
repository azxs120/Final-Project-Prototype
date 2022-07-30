package com.example.prototype;

import android.content.Context;
import android.os.StrictMode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Policy;
import java.util.HashMap;
import java.util.Map;

public class FCMSend {
    private static String BASE_URL="https://fcm.googleapis.com/fcm/send";
    private static String SERVER_KEY="key=AAAA2ewgi2M:APA91bEpaHF12sQrGP4suV4-BxcyOUuGxWGb4pP4ciQxmegnAj_w8_pSWpLkeUBZXUI9G8vIvmQEPdOfA8SaHrWk_q08pRMIWDCQCt5FVkmqgA3AnKkmqjjZv2pa-JKVacxZONyNLh0K";
    
    public static void pushNotification(Context context, String token, String title, String message){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        RequestQueue queue = Volley.newRequestQueue(context);

        try{
            JSONObject json = new JSONObject();
            json.put("to",token);
            JSONObject notification = new JSONObject();
            notification.put("title",title);
            notification.put("body",message);
            notification.put("notification",notification);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("FCM"+ response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                public Map<String,String> getHeaders() throws AuthFailureError{
                    Map<String,String > params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization",SERVER_KEY);
                    return params;
                }
            };

            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
