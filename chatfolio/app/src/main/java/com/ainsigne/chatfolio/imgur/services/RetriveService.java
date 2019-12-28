package com.ainsigne.chatfolio.imgur.services;

import android.telecom.Call;
import android.util.Log;

import com.ainsigne.chatfolio.imgur.Constants;

import com.ainsigne.chatfolio.imgur.imgurmodel.ImagesResponse;
import com.ainsigne.chatfolio.imgur.imgurmodel.ImgurAPI;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RetriveService {

    public void Execute(Callback callback)
    {
        buildRestAdapter().create(ImgurAPI.class).getImages(Constants.getClientAuth(), callback);
    }
    private RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurAPI.server)
                .build();

        /*
        Set rest adapter logging if we're already logging
        */
        if (Constants.LOGGING)
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;
    }
}
