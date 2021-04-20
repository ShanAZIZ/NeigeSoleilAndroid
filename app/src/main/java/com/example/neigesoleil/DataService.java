package com.example.neigesoleil;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.neigesoleil.models.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DataService {

    public static final String URL = "http://172.20.10.2:8000/api/";
    public static final String URL_GET_TOKEN = "token-auth/";
    public static final String URL_PROFILE = "user-profile/";

    static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DataService.context = context;
    }

    public interface TokenListener{
        void onError(String message);
        void onResponse(String message);
    }

    public static void getToken(String username, String password, TokenListener tk){
        String url = URL + URL_GET_TOKEN;
        JSONObject js = new JSONObject();
        try {
            js.put("username", username);
            js.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.getString("token");
                    String userId = response.getString("id");
                    String nom = response.getString("nom");
                    MainActivity.setAuthToken(token);
                    MainActivity.setUserId(userId);
                    tk.onResponse("Bienvenue " + nom);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error.networkResponse.statusCode == 400) {
                        tk.onError("Identifiant ou mot de passe incorrecte");
                    } else {
                        tk.onError("Erreur");
                    }
                }catch (Exception e) {
                    tk.onError("Une erreur est survenue");
                }
            }
        }){
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface ProfileListener{
        void onError(String message);
        void onResponse(String message);
    }

    public static void getProfile(String id, ProfileListener profileListener){
        String url = URL + URL_PROFILE + id + "/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                profileListener.onResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                profileListener.onError(error.toString());
            }
        }){
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Token "+ MainActivity.getAuthToken());
                return headers;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

}