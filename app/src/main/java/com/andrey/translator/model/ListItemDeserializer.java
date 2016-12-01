package com.andrey.translator.model;

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
        return null;
    }
}
