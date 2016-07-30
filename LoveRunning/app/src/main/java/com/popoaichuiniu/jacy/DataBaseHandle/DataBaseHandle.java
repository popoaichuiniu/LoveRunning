package com.popoaichuiniu.jacy.DataBaseHandle;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jacy on 2015/12/1.
 */
public class DataBaseHandle {
    private SQLiteDatabase readDataBase=null;
    private SQLiteDatabase writeDataBase=null;
    public DataBaseHandle(SQLiteDatabase readDataBase, SQLiteDatabase writeDataBase)
    {this.readDataBase=readDataBase;
        this.writeDataBase=writeDataBase;
    }

}
