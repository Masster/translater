package com.andrey.translator.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andrey.translator.R;
import com.andrey.translator.model.Favorite;
import com.andrey.translator.model.Lang;

import java.util.List;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class FavoriteAdapter extends BaseAdapter {

    private Context context;
    private List<Favorite> favorites;
    private OnFavPressed listener;

    public FavoriteAdapter(Context context, @NonNull OnFavPressed listener) {
        this.context = context;
        this.listener = listener;
        favorites = Favorite.getFavorites();
    }

    static class ViewHolder {
        TextView title;
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public Favorite getItem(int i) {
        return favorites.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LanguageAdapter.ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new LanguageAdapter.ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (LanguageAdapter.ViewHolder) view.getTag();
        }
        final Lang from = Lang.getLangById(getItem(i).getFromId());
        final Lang to = Lang.getLangById(getItem(i).getToId());
        viewHolder.title.setText(String.format("%1$s - %2$s", from.getTitle(), to.getTitle()));
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPressed(getItem(i));
            }
        });
        viewHolder.title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongPressed(getItem(i));
                return false;
            }
        });
        return view;
    }
}
