package com.dsfab.pattorisparmio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DbHelper extends SQLiteOpenHelper {
    public final static String dbName = "PattoRisparmio";
    final static int dbVersion = 1;
    final static String tableName = "Actions";
    final static String actionsIdColumn = "ID";
    final static String actionsTimestampColumn = "Timestamp";
    final static String actionsTypeColumn = "Type";
    public DbHelper(Context context){
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuilder query=new StringBuilder();
        query.append("CREATE TABLE "+tableName+ " (");
        query.append(actionsIdColumn+" integer primary key autoincrement,");
        query.append(actionsTimestampColumn+" int,");
        query.append(actionsTypeColumn+" int)");
        db.execSQL(query.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int a, int b){

    }
}