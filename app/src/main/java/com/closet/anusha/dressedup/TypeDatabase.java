package com.closet.anusha.dressedup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TypeDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="ftypea.db";
    public static final String TABLE_NAME="ftypea_table";
    public static final String col1="Type_id";
    public static final String col2="Table_num";
    public static final String col3="Formal";
    public static final String col4="Daily";
    public static final String col5="Traditional";
    public static final String col6="Party";
    public static final String col7="Western";
    public static final String col8="SpecialOcassion";
    public static final String col9="Others";


    public TypeDatabase(Context context)
    {
        super(context, DATABASE_NAME, null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+
                "( "+col1+" INTEGER PRIMARY KEY AUTOINCREMENT,  "
                +col2+" number,"+
                col3+" number, "+
                col4+" number,"+
                col5+" number,"+
                col6+" number, "+
                col7+" number,"+
                col8+" number, "+
                col9+" number"+
                ")" );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long insertData(int id, int table_num, int arr[]){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        if(id==0){
            contentValues.put(col1,id);
        }
        contentValues.put(col2,table_num);
        contentValues.put(col3,arr[0]);
        contentValues.put(col4,arr[1]);
        contentValues.put(col5,arr[2]);
        contentValues.put(col6,arr[3]);
        contentValues.put(col7,arr[4]);
        contentValues.put(col8,arr[5]);
        contentValues.put(col9,arr[6]);
        long result=database.insert(TABLE_NAME,null,contentValues);
        return result;

    }

    public Cursor getAllData(){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor1= database.rawQuery("select * from " + TABLE_NAME ,null);
        return cursor1;
    }
    public int updateInformation( int id , int dress_type, float rating, String last_worn, int no_of_times, int color, String comment, int available,int toporbot){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("dress_type", "Bob"); //These Fields should be your String values of actual column names
        cv.put("Rating", 1);
        cv.put("Last_worn", "Male");
        cv.put("No_of_times", 15); //These Fields should be your String values of actual column names
        cv.put("Color", "dmf");
        cv.put("Comment", "Male");
        cv.put("Avaiable", 1);
        cv.put("Top_Bottom", "fg");

      /*  contentValues.put(UserContract.NewUserInfo.USER_NAME,new_name);
        contentValues.put(UserContract.NewUserInfo.USER_MOB,mobile_name);
        contentValues.put(UserContract.NewUserInfo.USER_EMAIL,email_name);
        String selection = UserContract.NewUserInfo.USER_NAME+" LIKE ?";
        */
        int count = sqLiteDatabase.update(TABLE_NAME, cv, col1+"="+id, null);
        return count;
    }
}


