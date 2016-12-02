package com.andrey.translator.model;

import com.andrey.translator.BuildConfig;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class TranslateEngine {
    public static String getCurrentApiKey() {
        return BuildConfig.API_KEY;
    }
}
