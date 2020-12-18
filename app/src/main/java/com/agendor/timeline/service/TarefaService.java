package com.agendor.timeline.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.future.ResponseFuture;

public class TarefaService {

    private String endpoint = "gettarefas";
    private Context context;
    private ApiService apiService;

    public TarefaService(Context context) {
        this.context = context;
        this.apiService = new ApiService(this.context);
    }

    // get
    public ResponseFuture getAll (String idToken){
        return this.apiService.get(endpoint, idToken);
    }

    // POST
    public ResponseFuture post (JsonObject dados){
        return this.apiService.post(endpoint, dados);
    }
}
