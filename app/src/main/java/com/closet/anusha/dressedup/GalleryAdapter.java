package com.closet.anusha.dressedup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {
    Clothes contact;
    private Context mContext;
    int colorsValue[];
    static TextView mDotsText[];
    LinearLayout mDotsLayout;
    private int idColor;
    private List<Clothes> galleryRows;
    private LayoutInflater inflater;
    private ClothesDatabase handler;
    RatingBar ratingBar ;
    public GalleryAdapter(Context mContext, List<Clothes> galleryRows, ClothesDatabase handler) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.galleryRows = galleryRows;
        this.handler = handler;
    }
    @Override
    public int getCount() {
        return galleryRows.size();
    }

    @Override
    public Object getItem(int i) {
        return galleryRows.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        contact = galleryRows.get(position);
        View view = convertView;
        if(view == null)
            view = inflater.inflate(R.layout.item_gal, null);
        colorsValue = new int[]{Color.WHITE, Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW,
                Color.parseColor("#FF3F00"),Color.parseColor("#FF00FF")
                ,Color.parseColor("#BA55D3"),Color.parseColor("#773300"),
                Color.parseColor("#D3D3D3"),Color.parseColor("#DFDFDF"),
                Color.parseColor("#FFDF00")};
        ImageView mImageView = (ImageView) view.findViewById(R.id.img_pic);
        TextView mTextViewnoOfTimes = (TextView) view.findViewById(R.id.tv_noOfTimes);
        TextView mTextViewrating = (TextView) view.findViewById(R.id.tv_rating);
        TextView mTextViewavailability = (TextView) view.findViewById(R.id.tv_availablity);
        TextView mTextViewlastWorn = (TextView) view.findViewById(R.id.tv_lastWorn);
        TextView mTextViewcomment = (TextView) view.findViewById(R.id.tv_comment);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar_gal);
        mDotsLayout = (LinearLayout) view.findViewById(R.id.llt_col);
        ImageView del = (ImageView) view.findViewById(R.id.img_del);
        //Available Item
        if(contact.get_available()==1){
            mTextViewavailability.setText("Available");
            mTextViewlastWorn.setText("Last worn: "+contact.get_lastWorn());
            mTextViewcomment.setText("Comment: "+contact.get_comment());
        }
        //Borrowed Item
        else if (contact.get_available()==3) {
            mTextViewavailability.setText("Borrowed From " + contact.get_comment());
            mTextViewlastWorn.setText("Return on: "+contact.get_lastWorn());
            mTextViewcomment.setVisibility(View.GONE);
        }
        //Lent Item
        else{
            mTextViewavailability.setText("Lent To " + contact.get_comment());
            mTextViewlastWorn.setText("Collect on: "+contact.get_lastWorn());
            mTextViewcomment.setText("Unavailable");
        }
        mTextViewnoOfTimes.setText("No of times: "+contact.get_noOfTimes());
        idColor = contact.get_color();
        mDotsText = new TextView[13];
        mTextViewrating.setText("Rating: ");
        ratingBar.setRating((float)contact.get_rating());
        Bitmap bmp = BitmapFactory.decodeByteArray(contact.get_photograph(), 0, contact.get_photograph().length);
        mImageView.setImageBitmap(bmp);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = askOption();
                diaBox.show();
            }
        });
        openDatabase();
        return view;
    }


    private AlertDialog askOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(mContext)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.deletered)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(handler.deleteItem(contact.get_id())){
                            Intent intent = new Intent(mContext,GalleryActivity.class);
                            mContext.startActivity(intent);
                        }
                        else {
                            Toast.makeText(mContext,"Something went wrong.",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
    private void openDatabase() {
        Cursor cursor;
        SQLiteDatabase myDb;
        if(!doesDatabaseExist(mContext,"fcolors.db")) return;
        myDb = mContext.openOrCreateDatabase("fcolors.db",Context.MODE_PRIVATE, null);
        String tableName="fcolor_table";
        String query1=" SELECT * from "+tableName;
        cursor = myDb.rawQuery(query1, null);
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getInt(0)==idColor) break;
                System.out.println("FGD"+cursor.getInt(0)+idColor);
            } while (cursor.moveToNext());
        }
        for(int iter=3;iter<15;iter++){
            mDotsText[iter-3] = new TextView(mContext);
            mDotsText[iter-3].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            if(cursor.getInt(iter)==1){
                mDotsText[iter-3].setText("          ");
            }
            else{
                mDotsText[iter-3].setText("");
            }
            mDotsText[iter-3].setBackgroundColor(colorsValue[iter-3]);
            mDotsLayout.addView(mDotsText[iter-3]);
        }
        cursor.close();
    }
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

}
