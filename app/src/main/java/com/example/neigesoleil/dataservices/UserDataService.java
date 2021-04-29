package com.example.neigesoleil.dataservices;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.neigesoleil.MainActivity;
import com.example.neigesoleil.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserDataService extends DataService {


    public static final String URL_GET_TOKEN = "token-auth/";
    public static final String URL_PROFILE = "user-profile/";
    public static final String URL_SET_PROFILE = "profile/";



    public static void getToken(String username, String password, StringListener tk){
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

    public static void getProfile(String id, String token, StringListener profileListener){
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Token "+ token);
                return headers;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void setProfile(JSONObject profile, Boolean isProfile, String authToken, StringListener tk){
        String url = URL + URL_SET_PROFILE;
        int method;
        if (isProfile) {
            try {
                url += String.valueOf(profile.getInt("id")) + "/";
                method = 2; // PUT = 2
            } catch (JSONException e) {
                e.printStackTrace();
                method = 1; // POST = 1
            }
        } else {
            method = 1;
        }

        JsonObjectRequest request = new JsonObjectRequest(method, url, profile, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tk.onResponse("Profil à jour !");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error.networkResponse.statusCode == 400) {
                        tk.onError("Vérifiés les informations");
                    } else {
                        tk.onError("Erreur");
                    }
                }catch (Exception e) {
                    tk.onError("Une erreur est survenue");
                    e.printStackTrace();
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
                headers.put("Authorization", "Token "+ authToken);
                return headers;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }


}