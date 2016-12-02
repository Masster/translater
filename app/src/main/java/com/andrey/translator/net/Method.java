package com.andrey.translator.net;

import com.andrey.translator.net.retrofit.ListItemDeserializer;
import com.andrey.translator.net.retrofit.ListItemResponse;
import com.andrey.translator.net.retrofit.SingleItemDeserializer;
import com.andrey.translator.net.retrofit.SingleItemResponse;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public enum Method {
    GET_LANGS(ItemType.LIST) {
        @Override
        public Call request(ServerAPI serverAPI, HashMap<String, String> params) {
            return serverAPI.getLangs(
                    params.get("key"),
                    params.get("ui"));
        }
    },
    DETECT(ItemType.SINGLE) {
        @Override
        public Call request(ServerAPI serverAPI, HashMap<String, String> params) {
            return serverAPI.detect(
                    params.get("key"),
                    params.get("text"));
        }
    },
    TRANSLATE(ItemType.SINGLE) {
        @Override
        public Call request(ServerAPI serverAPI, HashMap<String, String> params) {
            return serverAPI.translate(
                    params.get("key"),
                    params.get("text"),
                    params.get("lang"));
        }
    };

    public abstract Call request(ServerAPI serverAPI, HashMap<String, String> params);

    public void sendRequest(HashMap<String, String> params, final API.OnRequestComplete listener) {
        RestClient restClient = null;
        ServerAPI serverAPI = null;
        switch (getItemType()) {
            case SINGLE:
                restClient = new RestClient(new SingleItemDeserializer());
                serverAPI = restClient.getServerAPI();
                final Call<SingleItemResponse> singleCall = request(serverAPI, params);
                singleCall.enqueue(new Callback<SingleItemResponse>() {
                    @Override
                    public void onResponse(Call<SingleItemResponse> call, Response<SingleItemResponse> response) {
                        if (response.isSuccessful()) {
                            listener.onSuccess(response.body());
                        } else {
                            try{
                                final JSONObject json = new JSONObject(response.errorBody().string());
                                listener.onError(new ResponseError(json.getInt("code"),json.getString("message")));
                            }catch (Exception e){
                                listener.onError(new ResponseError(response.code(), response.message()));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleItemResponse> call, Throwable t) {
                        listener.onError(new ResponseError());
                    }
                });
                break;
            case LIST:
                restClient = new RestClient(new ListItemDeserializer());
                serverAPI = restClient.getServerAPI();
                final Call<ListItemResponse> listCall = request(serverAPI, params);
                listCall.enqueue(new Callback<ListItemResponse>() {
                    @Override
                    public void onResponse(Call<ListItemResponse> call, Response<ListItemResponse> response) {
                        if (response.isSuccessful()) {
                            listener.onSuccess(response.body());
                        } else {
                            listener.onError(new ResponseError(response.code(), response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<ListItemResponse> call, Throwable t) {
                        listener.onError(new ResponseError());
                    }
                });
                break;
        }
    }

    private ItemType itemType;

    public ItemType getItemType() {
        return itemType;
    }

    Method(ItemType itemType) {
        this.itemType = itemType;
    }

    public enum ItemType {
        SINGLE,
        LIST
    }
}
