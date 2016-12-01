package com.andrey.translator.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Andrey Antonenko on 01.12.2016.
 */

public class Utils {
    public static byte[] serialize(Serializable object) {
        try {
            byte[] result;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(object);
            result = bos.toByteArray();
            out.close();
            bos.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialize(byte[] bytes) {
        try {
            T result;
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            result = (T) in.readObject();
            bis.close();
            in.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
