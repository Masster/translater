package com.andrey.translator.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andrey.translator.R;
import com.andrey.translator.model.Lang;

import java.util.List;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class LanguageAdapter extends BaseAdapter {

    private Context context;
    private List<Lang> langs;
    private OnLanguageSelected listener;

    static class ViewHolder {
        TextView title;
    }

    public LanguageAdapter(Context context, @NonNull OnLanguageSelected listener) {
        this.context = context;
        langs = Lang.getLangs();
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return langs.size();
    }

    @Override
    public Lang getItem(int i) {
        return langs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(String.valueOf(getItem(i).getTitle()));
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelect(getItem(i));
            }
        });
        return view;
    }
}
