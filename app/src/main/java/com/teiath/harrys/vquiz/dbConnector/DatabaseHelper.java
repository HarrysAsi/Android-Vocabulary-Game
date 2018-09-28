package com.teiath.harrys.vquiz.dbConnector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

/**
 * Created by harrys on 31/1/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "scores.db" ;
    public static final String TABLE_NAME = "scores" ;
    //TODO: variables for collumns
    public static final String ID = "id" ;
    public static final String NAME = "name" ;
    public static final String SURNAME = "surname" ;
    public static final String GENER = "gender" ;

    String SQL_QUERY = "CREATE TABLE " + TABLE_NAME + "(" + ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + NAME +" TEXT," +SURNAME + " TEXT);" ;


    //constructor for my db
    public DatabaseHelper(Context context) throws IOException {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating my database table
        db.execSQL(SQL_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



}
