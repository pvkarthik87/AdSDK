package com.glispa.adsdk.network;

import android.util.Log;

import com.glispa.adsdk.banner.BaseAd;
import com.glispa.adsdk.banner.ImageAd;
import com.glispa.adsdk.banner.NativeAd;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static com.glispa.adsdk.network.RequestManager.RequestType.Native;

public class RequestManager {

    private final Call<? extends BaseAd> getAdCall;

    public RequestManager(RequestType type) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getAdCall = type.equals(Native)
                ? retrofit.create(NativeAdService.class).getAd()
                : retrofit.create(InterstitialAdService.class).getAd();
    }

    public BaseAd getAd() {
        try {
            return getAdCall.clone().execute().body();
        } catch (IOException e) {
            Log.e("RequestManager", "Can't get NativeAd from server: " + e);
        }
        return null;
    }

    public enum RequestType {
        Native,
        Image
    }

    interface NativeAdService {
        @GET("/nativeAd")
        Call<NativeAd> getAd();
    }

    interface InterstitialAdService {
        @GET("/imageAd")
        Call<ImageAd> getAd();
    }


}
