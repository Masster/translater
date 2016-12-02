package com.andrey.translator.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrey.translator.R;
import com.andrey.translator.model.TranslateEngine;
import com.andrey.translator.net.API;
import com.andrey.translator.net.Method;
import com.andrey.translator.net.ResponseError;
import com.andrey.translator.net.retrofit.ResponseItem;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.translator_layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.yandex_service)
    public void openYandexService() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://translate.yandex.ru"));
        startActivity(browserIntent);
    }
}
