package com.andrey.translator.net.retrofit;

import com.activeandroid.ActiveAndroid;
import com.andrey.translator.model.Lang;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class ListItemDeserializer extends TypeAdapter<ListItemResponse> {
    @Override
    public void write(JsonWriter out, ListItemResponse value) throws IOException {

    }

    @Override
    public ListItemResponse read(JsonReader in) throws IOException {
        ListItemResponse response = new ListItemResponse();
        in.beginObject();
        while (in.hasNext()) {
            String currentToken = in.nextName();
            if (currentToken.equals("langs")) {
                in.beginObject();
                try {
                    ActiveAndroid.beginTransaction();
                    while (in.hasNext()) {
                        String name = in.nextName();
                        String value = in.nextString();
                        new Lang(name, value);
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
                in.endObject();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        return response;
    }
}
