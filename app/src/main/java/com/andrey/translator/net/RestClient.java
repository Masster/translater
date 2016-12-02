package com.andrey.translator.net;

import com.andrey.translator.BuildConfig;
import com.andrey.translator.net.retrofit.ListItemResponse;
import com.andrey.translator.net.retrofit.SingleItemDeserializer;
import com.andrey.translator.net.retrofit.SingleItemResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class RestClient {
    private static final String BASE_URL = BuildConfig.HOST;
    private ServerAPI serverAPI;

    public RestClient(TypeAdapter typeAdapter) {
        Gson gson;
        if (typeAdapter instanceof SingleItemDeserializer) {
            gson = new GsonBuilder().registerTypeAdapter(SingleItemResponse.class, typeAdapter)
                    .setDateFormat("yyyy-MM-dd")
                    .create();
        } else {
            gson = new GsonBuilder().registerTypeAdapter(ListItemResponse.class, typeAdapter)
                    .setDateFormat("yyyy-MM-dd")
                    .create();
        }

        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(interceptor);
        }

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        serverAPI = retrofit.create(ServerAPI.class);
    }

    public ServerAPI getServerAPI() {
        return serverAPI;
    }
}
