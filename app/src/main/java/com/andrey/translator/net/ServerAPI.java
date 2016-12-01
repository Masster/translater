package com.andrey.translator.net;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public interface ServerAPI {
    //getLangs?key=<API-ключ>&ui=<код языка>
    //detect?key=<API-ключ>&text=<текст>
    //translate?key=<API-ключ>&text=<переводимый текст>&lang=<направление перевода>

    @POST("getLangs")
    Call getLangs(@Field("key") String key, @Field("ui") String ui);

    @POST("detect")
    Call detect(@Field("key") String key, @Field("text") String text);

    @POST("translate")
    Call translate(@Field("key") String key, @Field("text") String text, @Field("lang") String lang);
}
