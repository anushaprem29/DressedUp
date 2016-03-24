package com.closet.anusha.dressedup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.List;


public class LastWorn extends AppCompatActivity {
    ImageView home,gallery,add,recent,user;
    ListView lv;
    List<Outfits> outfits;
    OutfitDatabase handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_worn);
        home = (ImageView) findViewById(R.id.bt_home4);
        gallery = (ImageView) findViewById(R.id.bt_gallery4);
        add = (ImageView) findViewById(R.id.bt_camera4);
        recent = (ImageView) findViewById(R.id.bt_recents4);
        handler = new OutfitDatabase(getApplicationContext());
        lv = (ListView) findViewById(R.id.lastwornlist);
        addClick();
        homeClick();
        galleryClick();
        if(doesDatabaseExist(this,"finoutfitdb.db"))
            loadOutfitData();
    }

    private void loadOutfitData() {
        outfits = handler.readAllOutfits();
        OutfitAdapter adapter = new OutfitAdapter(this,outfits,handler);
        lv.setAdapter(adapter);
    }

    private void addClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LastWorn.this,AddData.class);
                startActivity(intent);
            }
        });
    }

    private void homeClick() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LastWorn.this,MainScreen.class);
                startActivity(intent);
            }
        });
    }

    private void galleryClick() {
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LastWorn.this,GalleryActivity.class);
                startActivity(intent);
            }
        });
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
