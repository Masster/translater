package com.andrey.translator.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.Collections;
import java.util.List;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */
@Table(name = "Favorite")
public class Favorite extends Model {
    @Column
    String fromId;
    @Column
    String toId;

    public Favorite() {
        super();
    }

    public static Favorite getByIds(String from, String to) {
        return new Select().from(Favorite.class).where("fromId = ? AND toId = ?", from, to).executeSingle();
    }

    public Favorite(String from, String to) {
        super();
        this.fromId = from;
        this.toId = to;
        if (Favorite.getByIds(from, to) == null) {
            this.save();
        }
    }

    public static List<Favorite> getFavorites() {
        List<Favorite> items = new Select().from(Favorite.class).execute();
        if (items != null) {
            return items;
        } else {
            return Collections.emptyList();
        }
    }

    public static void delete(String from, String to) {
        new Delete().from(Favorite.class).where("fromId = ? AND toId = ?", from, to).executeSingle();
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }
}
