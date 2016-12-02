package com.andrey.translator.ui.dialog;

import com.andrey.translator.model.Favorite;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public interface OnFavPressed {
    void onPressed(Favorite favorite);

    void onLongPressed(Favorite favorite);
}
