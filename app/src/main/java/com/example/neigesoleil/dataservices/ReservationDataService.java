package com.example.neigesoleil.dataservices;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.neigesoleil.MySingleton;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ReservationDataService extends DataService{

    public static final String URL_GET_USER_RESERVATION = "user-reservation/";

    public static void getReservationList(String id, String authToken, DataService.StringListener listener) {
        String url = URL + URL_GET_USER_RESERVATION + id;
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
}
