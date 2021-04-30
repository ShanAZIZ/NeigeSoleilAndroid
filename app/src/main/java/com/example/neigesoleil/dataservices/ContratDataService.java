package com.example.neigesoleil.dataservices;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.neigesoleil.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContratDataService extends DataService {

    private static final String USER_CONTRATS_URL = "user-contrats/";
    private static final String CONTRAT_URL = "contrat/";

    public static void getContratList(String id, String authToken, DataService.StringListener listener) {
        String url = URL + USER_CONTRATS_URL + id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString());
                listener.onResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                listener.onError(error.getMessage());
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

    public static void getContrat(String id, String authToken, DataService.StringListener listener) {
        String url = URL + CONTRAT_URL + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                listener.onResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                listener.onError(error.getMessage());
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
