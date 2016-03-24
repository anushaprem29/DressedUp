package com.closet.anusha.dressedup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AccessoriesDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="faccessories.db";
    public static final String TABLE_NAME="facc_table";
    public static final String col1="id";
    public static final String col2="Image_id";
    public static final String col3="Typeid";
    public static final String col4="Rating";
    public static final String col5="Last_worn";
    public static final String col6="No_of_times";
    public static final String col7="Color_id";
    public static final String col8="Comment";
    public static final String col9="Available";
    public static final String col10="Range";
    private String[] columns= {col1,col2,col3,col4, col5,col6,col7,col8,col9,col10};

    public AccessoriesDatabase(Context context) {
        super(context, DATABASE_NAME, null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+
                "( "+col1+" INTEGER PRIMARY KEY AUTOINCREMENT,  "
                +col2+" blob,"+
                col3+" integer, "+
                col4+" number,"+
                col5+" varchar2(10),"+
                col6+" number, "+
                col7+" integer,"+
                col8+" varchar2(50), "+
                col9+" number,"+
                col10+" integer"+
                ")" );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(int id,byte[] image_id, int dress_type, float rating, String last_worn, int no_of_times, int color, String comment, int available, int toporbot){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        if(id==0){
            contentValues.put(col1,id);
        }
        contentValues.put(col2,image_id);
        contentValues.put(col3,dress_type);
        contentValues.put(col4,rating);
        contentValues.put(col5,last_worn);
        contentValues.put(col6,no_of_times);
        contentValues.put(col7,color);
        contentValues.put(col8,comment);
        contentValues.put(col9,available);
        contentValues.put(col10,toporbot);
        long result=database.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }
        return true;
    }

    public List<Accessories> readAllAccessories(){
        // Get db writable
        SQLiteDatabase db = this.getWritableDatabase();
        // Define contacts list
        List<Accessories> contacts = new ArrayList<Accessories>();
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Accessories contact = new Accessories();
            contact.set_id(Integer.parseInt(cursor.getString(0)));
            contact.set_photograph(cursor.getBlob(1));
            contact.set_type(Integer.parseInt(cursor.getString(2)));
            contact.set_rating(Double.parseDouble(cursor.getString(3)));
            contact.set_lastWorn(cursor.getString(4));
            contact.set_noOfTimes(Integer.parseInt(cursor.getString(5)));
            contact.set_color(Integer.parseInt(cursor.getString(6)));
            contact.set_comment(cursor.getString(7));
            contact.set_available(Integer.parseInt(cursor.getString(8)));
            contact.set_topBot(Integer.parseInt(cursor.getString(9)));
            contacts.add(contact);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return contacts;
    }

    public boolean deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME, col1 + " = ?",  new String[] { String.valueOf(id) });
        db.close();
        if(i != 0){
            return true;
        }else{
            return false;
        }
    }
}
