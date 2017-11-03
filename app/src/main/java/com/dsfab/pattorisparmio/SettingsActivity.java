package com.dsfab.pattorisparmio;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private List<Map<String, String>> SettingsViewList = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Impostazioni");
        ab.setDisplayHomeAsUpEnabled(true);
        ListView lv = (ListView) findViewById(R.id.SettingsList);
        lv.setAdapter(getSettingListAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = SettingsViewList.get(position).get("title");
                switch (selected){
                    case "Reset Dati":
                        resetDatabase();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private ListAdapter getSettingListAdapter() {
        SettingsViewList.add(new HashMap<String, String>() {
            {
                put("title", "Reset Dati");
                put("desc", "Rimuovi tutti i dati salvati");
            }
        });

        ListAdapter adapter = new SimpleAdapter(this, SettingsViewList,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "desc"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        return adapter;
    }

    private void resetDatabase() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attenzione")
                .setMessage("Confermare l'azione cancellerà tutte le azioni registrate in modo irreversibile")
                .setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getBaseContext().deleteDatabase(DbHelper.dbName);
                        Toast.makeText(SettingsActivity.this, "Dati cancellati", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
        builder.show();
    }
}
