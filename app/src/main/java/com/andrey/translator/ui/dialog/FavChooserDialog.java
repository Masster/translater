package com.andrey.translator.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.andrey.translator.R;
import com.andrey.translator.model.Favorite;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class FavChooserDialog extends DialogFragment {
    private View rootView;
    private FavoriteAdapter adapter;
    private OnFavPressed listener;

    public static FavChooserDialog getInstance(@NonNull OnFavPressed listener) {
        FavChooserDialog dialog = new FavChooserDialog();
        dialog.listener = listener;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_container, null);
        adapter = new FavoriteAdapter(getContext(), new OnFavPressed() {
            @Override
            public void onPressed(Favorite favorite) {
                listener.onPressed(favorite);
                FavChooserDialog.this.dismiss();
            }

            @Override
            public void onLongPressed(Favorite favorite) {
                listener.onLongPressed(favorite);
                FavChooserDialog.this.dismiss();
            }
        });
        ListView items = (ListView) rootView.findViewById(R.id.items);
        if (items != null) {
            items.setAdapter(adapter);
        }
        adb.setView(rootView);

        final AlertDialog result = adb.create();
        result.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return result;
    }
}
