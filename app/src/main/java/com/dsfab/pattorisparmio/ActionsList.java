package com.dsfab.pattorisparmio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ActionsList extends AppCompatActivity {
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions_list);
        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
        ListView lv = (ListView) findViewById(R.id.ActionsListView);
        lv.setAdapter(populateList());
    }

    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }
    private ListAdapter populateList(){
        Cursor cursor = db.rawQuery("select "+DbHelper.actionsIdColumn+" _id, "+DbHelper.actionsTimestampColumn + ", " + DbHelper.actionsTypeColumn+ " from "+DbHelper.tableName, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,     // Pass in the cursor to bind to.
                new String[] {DbHelper.actionsTypeColumn, DbHelper.actionsTimestampColumn}, // Array of cursor columns to bind to.
                new int[] {android.R.id.text1, android.R.id.text2});  // Parallel array of which template objects to bind to those columns.

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                TextView textView = (TextView) view;
                if (cursor.getColumnName(columnIndex).equals(DbHelper.actionsTimestampColumn)) {
                    String date = getDateCurrentTimeZone(cursor.getLong(columnIndex));
                    textView.setText(date);
                    return true;
                }
                if (cursor.getColumnName(columnIndex).equals(DbHelper.actionsTypeColumn)) {
                    String[] actionStrings = getResources().getStringArray(R.array.Actions);
                    textView.setText(actionStrings[Integer.parseInt(cursor.getString(columnIndex))]);
                    return true;
                }

                return false;
            }
        });
        return adapter;
    }
}
