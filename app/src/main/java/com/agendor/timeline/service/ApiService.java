package com.agendor.timeline.service;

import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

public class ApiService {
    public static String API_URL = "http://senhoraoferta.com/timeline/api/";
    private static Context context;

    public ApiService(Context context) {
        this.context = context;
    }

    // get
    public static ResponseFuture get (String endpoint, String idToken){
        return Ion.with(context)
                .load("GET", API_URL + endpoint)
                .asJsonArray();
        /*
        return Ion.with(context)
                .load("GET", API_URL + endpoint)
                .setHeader("Authorization","Bearer " + idToken)
                .asJsonArray();
        */
    }

    // post
    public static ResponseFuture<String> post (String endpoint, JsonObject data) {
        return Ion.with(context)
                .load("POST", API_URL + endpoint).setJsonObjectBody(data).asString();
    }

    // put

    // delete
}

