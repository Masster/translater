package com.andrey.translator.net;

import com.andrey.translator.net.retrofit.ListItemResponse;
import com.andrey.translator.net.retrofit.SingleItemResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public interface ServerAPI {
    @POST("getLangs")
    Call<ListItemResponse> getLangs(@Query("key") String key, @Query("ui") String ui);

    @POST("detect")
    Call<SingleItemResponse> detect(@Query("key") String key, @Query("text") String text);

    @POST("translate")
    Call<SingleItemResponse> translate(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);
}
