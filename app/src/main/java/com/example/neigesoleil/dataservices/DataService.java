package com.example.neigesoleil.dataservices;

import android.content.Context;

public class DataService {

    public static final String URL = "http://192.168.0.12:8000/api/";

    public static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        UserDataService.context = context;
    }

    public interface StringListener{
        void onError(String message);
        void onResponse(String message);
    }
}
