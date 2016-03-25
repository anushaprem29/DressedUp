package com.closet.anusha.dressedup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainScreen extends AppCompatActivity {
    ImageView home,gallery,add,recent,user;
    ImageView acc1,acc2,acc3,acc4,acc5,acc6,cloth1,cloth2;
    Button confirm;
    int ids[],noOfTimes[];
    int iter,id;
    OutfitDatabase odb;
    String date,repeat;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    byte[] byteImage1 = null;
    Cursor[] cursors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Home");
        setContentView(R.layout.activity_main_screen);
        ids=new int[8];
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        noOfTimes=new int[8];
        cursors = new Cursor[9];
        home = (ImageView) findViewById(R.id.bt_home);
        gallery = (ImageView) findViewById(R.id.bt_gallery);
        add = (ImageView) findViewById(R.id.bt_camera);
        recent = (ImageView) findViewById(R.id.bt_recents);
        confirm = (Button) findViewById(R.id.OutfitConfirmButton1);
        acc1 = (ImageView) findViewById(R.id.acc1);
        acc2 = (ImageView) findViewById(R.id.acc2);
        acc3 = (ImageView) findViewById(R.id.acc3);
        acc4 = (ImageView) findViewById(R.id.acc4);
        acc5 = (ImageView) findViewById(R.id.acc5);
        acc6 = (ImageView) findViewById(R.id.acc6);
        cloth1 = (ImageView) findViewById(R.id.cloth1);
        cloth2 = (ImageView) findViewById(R.id.cloth2);
        addAcc1(cloth1,"fclothesdress.db","fclothesdtable",7);
        addAcc1(acc1,"faccessories.db","facc_table",1);
        addAcc1(acc2,"faccessories.db","facc_table",2);
        addAcc1(acc3,"faccessories.db","facc_table",3);
        addAcc1(acc4,"fshoebag.db","fshoebag_table",4);
        addAcc1(acc5,"faccessories.db","facc_table",5);
        addAcc1(acc6,"fshoebag.db","fshoebag_table",6);
        addAcc1(cloth2,"fclothesdress.db","fclothesdtable",8);
        odb= new OutfitDatabase(this);
        if(doesDatabaseExist(this,"finoutfitdb.db")){
            id=1;
        }
        else id=0;
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.format(new Date());
                AlertDialog.Builder adb = new AlertDialog.Builder(MainScreen.this);
                LayoutInflater adbInflater = LayoutInflater.from(MainScreen.this);
                View eulaLayout = adbInflater.inflate(R.layout.datecheckbox, null);
                final CheckBox dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
                adb.setView(eulaLayout);
                adb.setTitle("Confirm")
                        .setMessage("Confirm Outfit for today?")
                        .setIcon(android.R.drawable.btn_star)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                for(iter=1;iter<=8;iter++) {
                                    if(cursors[iter]!=null){
                                        ids[iter-1]=cursors[iter].getInt(0);
                                        noOfTimes[iter-1]=cursors[iter].getInt(5) + 1;
                                    }
                                    else
                                        ids[iter-1]=-1;
                                }
                                if(dontShowAgain.isChecked()){
                                    showDialog(0);
                                }
                                else{
                                    repeat=null;
                                    boolean inserted = odb.insertData(id,ids,date,repeat);
                                    if(inserted){
                                        updateDbs();
                                        Toast.makeText(MainScreen.this, "Outfit Confirmed! :) ",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MainScreen.this, "Something went wrong! :(",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        addClick();
        galleryClick();
        lastWornClick();
    }
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            repeat=selectedYear+"-"+selectedMonth+"-"+selectedDay;
            boolean inserted = odb.insertData(id,ids,date,repeat);
            if(inserted){
                updateDbs();
                Toast.makeText(MainScreen.this, "Outfit Confirmed! :) ",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainScreen.this, "Something went wrong! :(",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void updateDbs() {
        String whereClauseArgument[] = new String[1];
        if(doesDatabaseExist(this,"fclothesdress.db")){
            ClothesDatabase clothesDbHelper = new ClothesDatabase(this);
            SQLiteDatabase sqliteCDatabase = clothesDbHelper.getWritableDatabase();
            ContentValues contentValues1 = new ContentValues();
            ContentValues contentValues2 = new ContentValues();
            contentValues1.put(ClothesDatabase.col5, date);
            contentValues1.put(ClothesDatabase.col6, noOfTimes[6]);
            contentValues2.put(ClothesDatabase.col5, date);
            contentValues2.put(ClothesDatabase.col6, noOfTimes[7]);
            whereClauseArgument[0] = "" + ids[6];
            System.out.println("whereClauseArguments :" + whereClauseArgument[0]);
            sqliteCDatabase.update(ClothesDatabase.TABLE_NAME, contentValues1, ClothesDatabase.col1 + "=?", whereClauseArgument);
            whereClauseArgument[0] = "" + ids[7];
            System.out.println("whereClauseArguments :" + whereClauseArgument[0]);
            sqliteCDatabase.update(ClothesDatabase.TABLE_NAME, contentValues2, ClothesDatabase.col1 + "=?", whereClauseArgument);
            sqliteCDatabase.close();
        }
        if(doesDatabaseExist(this,"faccessories.db")){
            AccessoriesDatabase accDbHelper = new AccessoriesDatabase(this);
            SQLiteDatabase sqliteADatabase = accDbHelper.getWritableDatabase();
            ContentValues contentValues3 = new ContentValues();
            ContentValues contentValues4 = new ContentValues();
            ContentValues contentValues5 = new ContentValues();
            ContentValues contentValues6 = new ContentValues();
            contentValues3.put(AccessoriesDatabase.col5, date.toString());
            contentValues3.put(AccessoriesDatabase.col6, noOfTimes[0]);
            contentValues4.put(AccessoriesDatabase.col5, date.toString());
            contentValues4.put(AccessoriesDatabase.col6, noOfTimes[1]);
            contentValues5.put(AccessoriesDatabase.col5, date.toString());
            contentValues5.put(AccessoriesDatabase.col6, noOfTimes[2]);
            contentValues6.put(AccessoriesDatabase.col5, date.toString());
            contentValues6.put(AccessoriesDatabase.col6, noOfTimes[4]);
            whereClauseArgument[0] = "" + ids[0];
            System.out.println("whereClauseArguments :" + whereClauseArgument[0]);
            System.out.println("AccessoriesDatabase :" + contentValues3.getAsString(AccessoriesDatabase.col5));
            System.out.println("AccessoriesDatabase :" + contentValues3.getAsInteger(AccessoriesDatabase.col6));
            sqliteADatabase.update(AccessoriesDatabase.TABLE_NAME, contentValues3, AccessoriesDatabase.col1 + "=?", whereClauseArgument);
            whereClauseArgument[0] = ""+ids[1];
            System.out.println("whereClauseArguments :" + whereClauseArgument[0]);
            sqliteADatabase.update(AccessoriesDatabase.TABLE_NAME, contentValues4, AccessoriesDatabase.col1 + "=?", whereClauseArgument);
            whereClauseArgument[0] = "" + ids[2];
            System.out.println("whereClauseArguments :" + whereClauseArgument[0]);
            sqliteADatabase.update(AccessoriesDatabase.TABLE_NAME, contentValues5, AccessoriesDatabase.col1 + "=?", whereClauseArgument);
            whereClauseArgument[0] = "" + ids[4];
            System.out.println("whereClauseArguments :" + whereClauseArgument[0]);
            sqliteADatabase.update(AccessoriesDatabase.TABLE_NAME, contentValues6, AccessoriesDatabase.col1 + "=?", whereClauseArgument);
            sqliteADatabase.close();
        }

        if(doesDatabaseExist(this,"fshoebag.db")) {
            ShoeBagDatabase shoeDbHelper = new ShoeBagDatabase(this);
            SQLiteDatabase sqliteSDatabase = shoeDbHelper.getWritableDatabase();
            ContentValues contentValues7 = new ContentValues();
            ContentValues contentValues8 = new ContentValues();
            contentValues7.put(ShoeBagDatabase.col5, date.toString());
            contentValues7.put(ShoeBagDatabase.col6, noOfTimes[3]);
            contentValues8.put(ShoeBagDatabase.col5, date.toString());
            contentValues8.put(ShoeBagDatabase.col6, noOfTimes[5]);
            whereClauseArgument[0] = "" + ids[3];
            System.out.println("whereClauseArguments :" + whereClauseArgument[0]);
            sqliteSDatabase.update(ShoeBagDatabase.TABLE_NAME, contentValues7, ShoeBagDatabase.col1 + "=?", whereClauseArgument);
            whereClauseArgument[0] = "" + ids[5];
            System.out.println("whereClauseArguments :" + whereClauseArgument[0]);
            sqliteSDatabase.update(ShoeBagDatabase.TABLE_NAME, contentValues8, ShoeBagDatabase.col1 + "=?", whereClauseArgument);
            sqliteSDatabase.close();
        }
    }
    private void addClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this,AddData.class);
                startActivity(intent);
            }
        });
    }

    private void galleryClick() {
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this,GalleryActivity.class);
                startActivity(intent);
            }
        });
    }
    private void addAcc1(ImageView imageView,String dbName, String tableName,int i) {
        if(doesDatabaseExist(this,dbName)){
            addImage(imageView,dbName,tableName,i);
        }
        else{
        }
    }
    private void addImage(final ImageView imageView, String dbName, String tableName, final int i) {
        SQLiteDatabase myDb;
        cursors[i]=null;
        byteImage1=null;
        myDb = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        if(i==1){
            String query1=" SELECT * from "+tableName+" WHERE "+tableName+".Range=0";
            cursors[i]=myDb.rawQuery(query1, null);
            if(cursors[i].getCount()==0){
                cursors[i]=null;
            }
        }
        else if(i==2){
            String query1=" SELECT * from "+tableName+" WHERE "+tableName+".Range=1";
            cursors[i]=myDb.rawQuery(query1, null);
            if(cursors[i].getCount()==0){
                cursors[i]=null;
            }
        }
        else if(i==3){
            String query1=" SELECT * from "+tableName+" WHERE "+tableName+".Range>1 AND "+tableName+".Range<5";
            cursors[i]=myDb.rawQuery(query1, null);
            if(cursors[i].getCount()==0){
                cursors[i]=null;
            }
        }
        else if(i==5){
            String query1=" SELECT * from "+tableName+" WHERE "+tableName+".Range>4 AND "+tableName+".Range<9";
            cursors[i]=myDb.rawQuery(query1, null);
            if(cursors[i].getCount()==0){
                cursors[i]=null;
            }
        }
        else if(i==4){
            String query1=" SELECT * from "+tableName+" WHERE "+tableName+".Range=0 ";
            cursors[i]=myDb.rawQuery(query1, null);
            if(cursors[i].getCount()==0){
                cursors[i]=null;
            }
        }
        else if(i==6){
            String query1=" SELECT * from "+tableName+" WHERE "+tableName+".Range=1";
            cursors[i]=myDb.rawQuery(query1, null);
            if(cursors[i].getCount()==0){
                cursors[i]=null;
            }
        }

        else if(i==7){
            String query1=" SELECT * from "+tableName+" WHERE "+tableName+".Range=0 OR "+tableName+".Range=2";
            cursors[i]=myDb.rawQuery(query1, null);
            if(cursors[i].getCount()==0){
                cursors[i]=null;
            }
        }

        else if(i==8){
            String query1=" SELECT * from "+tableName+" WHERE "+tableName+".Range=1";
            cursors[i]=myDb.rawQuery(query1, null);
            if(cursors[i].getCount()==0){
                cursors[i]=null;
            }
        }
        if(cursors[i]!=null){
            cursors[i].moveToFirst();
            byteImage1=cursors[i].getBlob(1);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteImage1, 0,
                    byteImage1.length));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cursors[i].moveToNext()) {
                        byteImage1 = cursors[i].getBlob(1);
                        imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteImage1, 0, byteImage1.length));
                    }
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (cursors[i].moveToPrevious()) {
                        byteImage1 = cursors[i].getBlob(1);
                        imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteImage1, 0,
                                byteImage1.length));
                    }
                    return true;
                }
            });
        }
        else {
        }
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private void lastWornClick() {
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this,LastWorn.class);
                startActivity(intent);
            }
        });
    }
}
