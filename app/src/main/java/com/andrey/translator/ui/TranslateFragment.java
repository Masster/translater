package com.andrey.translator.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrey.translator.R;
import com.andrey.translator.model.Favorite;
import com.andrey.translator.model.Lang;
import com.andrey.translator.model.TranslateEngine;
import com.andrey.translator.net.API;
import com.andrey.translator.net.Method;
import com.andrey.translator.net.ResponseError;
import com.andrey.translator.net.retrofit.ResponseItem;
import com.andrey.translator.net.retrofit.SingleItemResponse;
import com.andrey.translator.ui.dialog.ChooserLanguageDialog;
import com.andrey.translator.ui.dialog.FavChooserDialog;
import com.andrey.translator.ui.dialog.OnFavPressed;
import com.andrey.translator.ui.dialog.OnLanguageSelected;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Andrey Antonenko on 01.12.2016.
 */

public class TranslateFragment extends Fragment {

    @BindView(R.id.from_lang)
    TextView fromLang;
    @BindView(R.id.to_lang)
    TextView toLang;
    @BindView(R.id.switch_lang)
    ImageView switchLang;
    @BindView(R.id.input_text)
    EditText inputText;
    @BindView(R.id.out_text)
    TextView outText;
    @BindView(R.id.translate)
    Button translate;
    @BindView(R.id.out_container)
    CardView outContainer;
    @BindView(R.id.yandex_service)
    TextView yandexService;
    @BindView(R.id.auto)
    TextView auto;
    @BindView(R.id.favorite)
    ImageView favorite;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.emptyView)
    RelativeLayout emptyView;

    private Lang fromLangSelected;
    private Lang toLangSelected;
    private ProgressDialog dlg;
    private boolean isAuto;

    private IntentFilter connectivityChangeFilter;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityChangeFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.translator_layout, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        dlg = new ProgressDialog(getContext());
        dlg.setTitle(R.string.dialog_title);
        dlg.setMessage(getContext().getString(R.string.dialog_message));
        dlg.setCancelable(false);

        return rootView;
    }

    private BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateInfo();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(connectionReceiver, connectivityChangeFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(connectionReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateInfo();
    }

    private void updateInfo() {
        dlg.show();
        final HashMap<String, String> params = new HashMap<>();
        params.put("key", TranslateEngine.getCurrentApiKey());
        params.put("ui", "ru");

        API.sendRequest(Method.GET_LANGS, params, new API.OnRequestComplete() {
            @Override
            public void onSuccess(ResponseItem response) {
                content.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                toLangSelected = Lang.getLangById("ru");
                fromLangSelected = Lang.getLangById("en");
                isAuto = false;
                updateViews();
                dlg.dismiss();
            }

            @Override
            public void onError(ResponseError error) {
                content.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });
    }

    @OnClick(R.id.yandex_service)
    public void openYandexService() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://translate.yandex.ru"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.from_lang)
    public void openFromLang() {
        final ChooserLanguageDialog dialog = ChooserLanguageDialog.getInstance(new OnLanguageSelected() {
            @Override
            public void onSelect(Lang lang) {
                fromLangSelected = lang;
                updateViews();
            }
        });
        dialog.show(getFragmentManager(), "");
    }

    @OnClick(R.id.to_lang)
    public void openToLang() {
        final ChooserLanguageDialog dialog = ChooserLanguageDialog.getInstance(new OnLanguageSelected() {
            @Override
            public void onSelect(Lang lang) {
                toLangSelected = lang;
                updateViews();
            }
        });
        dialog.show(getFragmentManager(), "");
    }

    @OnClick(R.id.switch_lang)
    public void switchLangs() {
        if (fromLangSelected != null) {
            final Lang tmp = fromLangSelected;
            fromLangSelected = toLangSelected;
            toLangSelected = tmp;
            updateViews();
        }
    }

    @OnClick(R.id.favorite)
    public void onFavClick() {

        new Favorite(fromLangSelected.getLangId(), toLangSelected.getLangId());

        final FavChooserDialog dialog = FavChooserDialog.getInstance(new OnFavPressed() {
            @Override
            public void onPressed(Favorite favorite) {
                fromLangSelected = Lang.getLangById(favorite.getFromId());
                toLangSelected = Lang.getLangById(favorite.getToId());
                updateViews();
            }

            @Override
            public void onLongPressed(Favorite favorite) {
                deleteFav(favorite);
            }
        });
        dialog.show(getFragmentManager(), "");
    }

    @OnClick(R.id.translate)
    public void translate() {
        final String text = inputText.getText().toString();
        if (!TextUtils.isEmpty(text) && toLangSelected != null) {
            dlg.show();
            final HashMap<String, String> params = new HashMap<>();
            params.put("key", TranslateEngine.getCurrentApiKey());
            params.put("text", text);
            if (fromLangSelected != null && !isAuto) {
                params.put("lang", String.format("%1$s-%2$s", fromLangSelected.getLangId(), toLangSelected.getLangId()));
            } else {
                params.put("lang", toLangSelected.getLangId());
            }

            API.sendRequest(Method.TRANSLATE, params, new API.OnRequestComplete() {
                @Override
                public void onSuccess(ResponseItem response) {
                    String out = ((SingleItemResponse) response).text;
                    outText.setText(out);
                    outContainer.setVisibility(View.VISIBLE);
                    dlg.dismiss();
                }

                @Override
                public void onError(ResponseError error) {
                    Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                }
            });
        }
    }

    @OnClick(R.id.auto)
    public void autoClick() {
        isAuto = !isAuto;
        updateViews();
    }

    private void updateViews() {
        if (isAuto) {
            auto.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
        } else {
            auto.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
        if (fromLangSelected != null) {
            fromLang.setText(fromLangSelected.getTitle());
        }
        if (toLangSelected != null) {
            toLang.setText(toLangSelected.getTitle());
        }
    }

    private void deleteFav(final Favorite favorite) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.delete_message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        favorite.delete();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}
