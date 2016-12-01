package com.andrey.translator;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.andrey.translator.model.ListTypeSerializer;

/**
 * Created by Andrey Antonenko on 01.12.2016.
 */

public class MainApp extends com.activeandroid.app.Application {
    private static MainApp instance;

    public static MainApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        ActiveAndroid.initialize(new Configuration
                .Builder(this)
                .setDatabaseName("translator.db")
                .setDatabaseVersion(1)
                .addTypeSerializer(ListTypeSerializer.class)
                .create(), false);
    }
}
