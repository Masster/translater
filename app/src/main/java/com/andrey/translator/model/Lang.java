package com.andrey.translator.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by Andrey Antonenko on 02.12.2016.
 */

public class Lang extends Model {
    @Column
    String langId;
    @Column
    String title;
}
