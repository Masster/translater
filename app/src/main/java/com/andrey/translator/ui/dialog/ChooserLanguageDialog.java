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
import com.andrey.translator.model.Lang;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class ChooserLanguageDialog extends DialogFragment {

    private View rootView;
    private LanguageAdapter adapter;
    private OnLanguageSelected listener;

    public static ChooserLanguageDialog getInstance(@NonNull OnLanguageSelected listener) {
        ChooserLanguageDialog dialog = new ChooserLanguageDialog();
        dialog.listener = listener;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_container, null);
        adapter = new LanguageAdapter(getContext(), new OnLanguageSelected() {
            @Override
            public void onSelect(Lang lang) {
                listener.onSelect(lang);
                ChooserLanguageDialog.this.dismiss();
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
