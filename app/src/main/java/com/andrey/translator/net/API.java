package com.andrey.translator.net;

import android.support.annotation.NonNull;

import com.andrey.translator.net.retrofit.ResponseItem;
import com.andrey.translator.utils.Utils;

import java.util.HashMap;

/**
 * Created by Andrey Antonenko on 01.12.2016.
 */

public class API {
    public interface OnRequestComplete {
        void onSuccess(ResponseItem response);

        void onError(ResponseError error);
    }

    public static void sendRequest(Method method, HashMap<String, String> params, @NonNull final OnRequestComplete listener) {

        if (!Utils.hasConnection()) {
            listener.onError(new ResponseError(ServerError.NO_CONNECTION));
            return;
        }
        method.sendRequest(params, new OnRequestComplete() {
            @Override
            public void onSuccess(ResponseItem response) {
                listener.onSuccess(response);
            }

            @Override
            public void onError(ResponseError error) {
                listener.onError(error);
            }
        });
    }
}
