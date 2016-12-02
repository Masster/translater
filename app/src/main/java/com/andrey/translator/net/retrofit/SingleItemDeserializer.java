package com.andrey.translator.net.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class SingleItemDeserializer extends TypeAdapter<SingleItemResponse> {
    @Override
    public void write(JsonWriter out, SingleItemResponse value) throws IOException {

    }

    @Override
    public SingleItemResponse read(JsonReader in) throws IOException {
        SingleItemResponse response = new SingleItemResponse();
        in.beginObject();
        while (in.hasNext()) {
            String currentToken = in.nextName();
            if (currentToken.equals("code")) {
                response.code = in.nextInt();
            } else if (currentToken.equals("text")) {
                in.beginArray();
                final Gson gson = new GsonBuilder().create();
                in.hasNext();
                response.text = gson.fromJson(in, String.class);
                in.endArray();
            } else if (currentToken.equals("message")) {
                response.message = in.nextString();
            } else if (currentToken.equals("lang")) {
                response.lang = in.nextString();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        return response;
    }
}
