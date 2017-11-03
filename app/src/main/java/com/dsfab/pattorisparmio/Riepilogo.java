package com.dsfab.pattorisparmio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class Riepilogo extends AppCompatActivity {
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riepilogo);

    }

    @Override
    protected void onStart(){
        super.onStart();
        updateTextViews();
    }
    @Override
    protected void onResume(){
        super.onResume();
        updateTextViews();
    }
    private void updateTextViews(){
        Cursor cursor = db.rawQuery("select * from "+DbHelper.tableName, null);
        int count = cursor.getCount();
        TextView actiontxt = (TextView) findViewById(R.id.Actions);
        actiontxt.setText(String.valueOf(count));
        TextView savingstxt = (TextView) findViewById(R.id.Savings);
        savingstxt.setText("€"+ df.format(count*0.10));
        cursor.close();
    }


    public void addAction(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleziona tipo azione ecologica")
                .setItems(R.array.Actions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        ContentValues values = new ContentValues();
                        values.put(DbHelper.actionsTimestampColumn, System.currentTimeMillis());
                        values.put(DbHelper.actionsTypeColumn, which);
                        db.insert(DbHelper.tableName, null, values);
                        updateTextViews();
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();

    }

    public void openList(View view){
        Intent intent= new Intent(this,ActionsList.class);
        startActivity(intent);
    }
}