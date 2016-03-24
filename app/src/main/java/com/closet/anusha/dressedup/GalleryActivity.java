package com.closet.anusha.dressedup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.util.List;


public class GalleryActivity extends AppCompatActivity {
    ImageView home,gallery,add,recent,user;
    Button bt_clo,bt_acc,bt_shoe;
    int chooser;
    ListView lv;
    private List<Clothes> clothes;
    private List<Accessories> accessories;
    private List<ShoeBag> shoes;
    ClothesDatabase handler;
    AccessoriesDatabase handler2;
    ShoeBagDatabase handler3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        home = (ImageView) findViewById(R.id.bt_home2);
        gallery = (ImageView) findViewById(R.id.bt_gallery2);
        add = (ImageView) findViewById(R.id.bt_camera2);
        recent = (ImageView) findViewById(R.id.bt_recents2);
        handler = new ClothesDatabase(getApplicationContext());
        handler2 = new AccessoriesDatabase(getApplicationContext());
        handler3 = new ShoeBagDatabase(getApplicationContext());
        lv = (ListView) findViewById(R.id.gal_listview);
        loadClothesData();
        bt_clo = (Button) findViewById(R.id.bt_clo);
        bt_acc = (Button) findViewById(R.id.bt_acc);
        bt_shoe = (Button) findViewById(R.id.bt_shoesbag);
        bt_clo.setBackgroundResource(R.drawable.buttonbg);
        bt_clo.setTextColor(Color.parseColor("#ffffff"));
        bt_acc.setTextColor(Color.parseColor("#000000"));
        bt_shoe.setTextColor(Color.parseColor("#000000"));
        addClick();
        homeClick();
        clothesClick();
        accClick();
        shoesClick();
        if(doesDatabaseExist(GalleryActivity.this,"fclothesdress.db")) {
            lastWornClick();
        }
    }



    private void shoesClick() {
        bt_shoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doesDatabaseExist(GalleryActivity.this,"fshoebag.db")){
                    chooser=3;
                    bt_shoe.setBackgroundResource(R.drawable.buttonbg);
                    bt_shoe.setTextColor(Color.parseColor("#ffffff"));
                    bt_clo.setTextColor(Color.parseColor("#000000"));
                    bt_acc.setTextColor(Color.parseColor("#000000"));
                    bt_clo.setBackgroundResource(R.drawable.view_bg);
                    bt_acc.setBackgroundResource(R.drawable.view_bg);
                    loadShoesData();
                }
                else
                    Toast.makeText(GalleryActivity.this,"Add shoes or bag to your gallery :)",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void accClick() {
        bt_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doesDatabaseExist(GalleryActivity.this,"faccessories.db")){
                    chooser=2;
                    bt_acc.setBackgroundResource(R.drawable.buttonbg);
                    bt_acc.setTextColor(Color.parseColor("#ffffff"));
                    bt_clo.setTextColor(Color.parseColor("#000000"));
                    bt_shoe.setTextColor(Color.parseColor("#000000"));
                    bt_clo.setBackgroundResource(R.drawable.view_bg);
                    bt_shoe.setBackgroundResource(R.drawable.view_bg);
                    loadAccessoriesData();
                }
                else
                    Toast.makeText(GalleryActivity.this,"Add accessories to your gallery :)",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clothesClick() {
        bt_clo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doesDatabaseExist(GalleryActivity.this,"fclothesdress.db")) {
                    chooser=1;
                    bt_clo.setBackgroundResource(R.drawable.buttonbg);
                    bt_clo.setTextColor(Color.parseColor("#ffffff"));
                    bt_acc.setTextColor(Color.parseColor("#000000"));
                    bt_shoe.setTextColor(Color.parseColor("#000000"));
                    bt_acc.setBackgroundResource(R.drawable.view_bg);
                    bt_shoe.setBackgroundResource(R.drawable.view_bg);
                    loadClothesData();
                } else
                    Toast.makeText(GalleryActivity.this,"Add clothes to your gallery :)",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadClothesData() {
        clothes = handler.readAllClothes();
        GalleryAdapter adapter = new GalleryAdapter(this,clothes,handler);
        lv.setAdapter(adapter);
        for(Clothes c : clothes){
            String record = "ID=" + c.get_id() + " | Type=" + c.get_type() + " | " + c.get_topBot();
            Log.d("Record",record);
        }
    }
    private void loadAccessoriesData() {
        accessories = handler2.readAllAccessories();
        GalleryAdapterA adapter = new GalleryAdapterA(this,accessories,handler2);
        lv.setAdapter(adapter);
        for(Accessories c : accessories){
            String record = "ID=" + c.get_id() + " | Type=" + c.get_type() + " | " + c.get_topBot();
            Log.d("Record",record);
        }
    }
    private void loadShoesData() {
        shoes = handler3.readAllShoeBag();
        GalleryAdapterS adapter = new GalleryAdapterS(this,shoes,handler3);
        lv.setAdapter(adapter);
        for(ShoeBag c : shoes){
            String record = "ID=" + c.get_id() + " | Type=" + c.get_type() + " | " + c.get_topBot();
            Log.d("Record",record);
        }
    }

    private void addClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this,AddData.class);
                startActivity(intent);
            }
        });
    }

    private void homeClick() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this,MainScreen.class);
                startActivity(intent);
            }
        });
    }

    private void lastWornClick() {
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this,LastWorn.class);
                startActivity(intent);
            }
        });
    }
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
