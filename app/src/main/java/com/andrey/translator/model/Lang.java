package com.andrey.translator.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Collections;
import java.util.List;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */
@Table(name = "Lang")
public class Lang extends Model {
    @Column
    String langId;
    @Column
    String title;

    public Lang() {
        super();
    }

    public static boolean isHere(String name) {
        return new Select().from(Lang.class).where("langId = ?", name).exists();
    }

    public Lang(String name, String value) {
        super();
        this.langId = name;
        this.title = value;
        if (!isHere(name)) {
            this.save();
        }
    }

    public String getLangId() {
        return langId;
    }

    public String getTitle() {
        return title;
    }

    public static String getTitle(String langId) {
        final Lang lang = new Select().from(Lang.class).where("langId = ?", langId).executeSingle();
        if (lang != null) {
            return lang.getTitle();
        } else {
            return "";
        }
    }

    public static List<Lang> getLangs() {
        List<Lang> langs = new Select().from(Lang.class).execute();
        if (langs != null) {
            return langs;
        } else {
            return Collections.emptyList();
        }
    }
}
