package com.android.ram.rideindia.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.ram.rideindia.db.UserRegisterDbHelper;

import androidx.annotation.Nullable;

public class addStudentDbHelper extends SQLiteOpenHelper {

    private  static final int DATABASE_VERSION=1;
    //private String CREATE_TABEL_QUERY="CREATE TABLE "++""
    public addStudentDbHelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
