package com.andrey.translator.model;

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
        return null;
    }
}
