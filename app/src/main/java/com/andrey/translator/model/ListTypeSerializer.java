package com.andrey.translator.model;

import com.activeandroid.serializer.TypeSerializer;
import com.andrey.translator.utils.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrey Antonenko on 01.12.2016.
 */

public class ListTypeSerializer extends TypeSerializer {
    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return byte[].class;
    }

    @Override
    public Object serialize(Object o) {
        return Utils.serialize((Serializable) o);
    }

    @Override
    public Object deserialize(Object o) {
        return Utils.deserialize((byte[]) o);
    }
}
