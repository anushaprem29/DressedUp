package com.closet.anusha.dressedup;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.List;

public class OutfitAdapter extends BaseAdapter {
    Outfits contact;
    private Context mContext;
    private List<Outfits> outfitRows;
    private LayoutInflater inflater;
    private OutfitDatabase handler;
    private boolean flag=false;

    public OutfitAdapter(Context mContext, List<Outfits> galleryRows, OutfitDatabase handler) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.outfitRows = galleryRows;
        this.handler = handler;
    }
    @Override
    public int getCount() {
        return outfitRows.size();
    }

    @Override
    public Object getItem(int i) {
        return outfitRows.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        contact = outfitRows.get(position);
        View view = convertView;
        if(view == null)
            view = inflater.inflate(R.layout.single_last_worn, null);
        Gallery gallery = (Gallery)view.findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(mContext,position,convertView,parent));
        ImageView del = (ImageView) view.findViewById(R.id.img_del_lw);
        TextView dateTv = (TextView) view.findViewById(R.id.dateText);
        dateTv.setText("Worn On:  "+contact.getDate());
        System.out.println("DATE"+contact.getDate());
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = askOption();
                diaBox.show();
            }
        });
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
                        if(handler.deleteItem(contact.getId())){
                            Intent intent = new Intent(mContext,LastWorn.class);
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

    private byte [] openDatabase(String dbName,String tableName,int idPic) {
        Cursor cursor;
        SQLiteDatabase myDb;
        if(idPic==-1)   return null;
        if(!doesDatabaseExist(mContext,dbName)) return null;
        myDb = mContext.openOrCreateDatabase(dbName,Context.MODE_PRIVATE, null);
        String query1=" SELECT * from "+tableName;
        cursor = myDb.rawQuery(query1, null);
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getInt(0)==idPic){
                    flag=true;
                    break;
                }
                System.out.println("FGD"+cursor.getInt(0)+idPic);
            } while (cursor.moveToNext());
        }
        byte returnVal[]=null;
        if(flag)    returnVal=cursor.getBlob(cursor.getColumnIndex("Image_id"));
        cursor.close();
        return returnVal;
    }
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private Bitmap[] mImageIds = new Bitmap[8];
        Outfits contact;

        //constructor
        public ImageAdapter (Context c,int position, View convertView, ViewGroup parent) {
            mContext = c;
            contact = outfitRows.get(position);
            if(doesDatabaseExist(mContext,"faccessories.db")) {
                byte img1[] = openDatabase("faccessories.db", "facc_table", contact.getAccId1());
                if (img1 != null) {
                    Bitmap bmp1 = BitmapFactory.decodeByteArray(img1, 0, img1.length);
                    mImageIds[6] = bmp1;
                }
                byte img2[] = openDatabase("faccessories.db", "facc_table", contact.getAccId2());
                if (img2 != null) {
                    Bitmap bmp2 = BitmapFactory.decodeByteArray(img2, 0, img2.length);
                    mImageIds[7] = bmp2;
                }
                byte img3[] = openDatabase("faccessories.db", "facc_table", contact.getAccId3());
                if (img3 != null) {
                    Bitmap bmp3 = BitmapFactory.decodeByteArray(img3, 0, img3.length);
                    mImageIds[2] = bmp3;
                }
                byte img5[] = openDatabase("faccessories.db", "facc_table", contact.getAccId4());
                if(img5!=null) {
                    Bitmap bmp5 = BitmapFactory.decodeByteArray(img5, 0, img5.length);
                    mImageIds[4] = bmp5;
                }
            }
            if(doesDatabaseExist(mContext,"fshoebag.db")) {
                byte img4[] = openDatabase("fshoebag.db", "fshoebag_table", contact.getShoeId1());
                if (img4 != null) {
                    Bitmap bmp4 = BitmapFactory.decodeByteArray(img4, 0, img4.length);
                    mImageIds[3] = bmp4;
                }
                byte img6[] = openDatabase("fshoebag.db","fshoebag_table",contact.getShoeId2());
                if (img6!=null) {
                    Bitmap bmp6 = BitmapFactory.decodeByteArray(img6, 0, img6.length);
                    mImageIds[5] = bmp6;
                }
            }
            if(doesDatabaseExist(mContext,"fclothesdress.db")) {
                byte img7[] = openDatabase("fclothesdress.db", "fclothesdtable", contact.getClothId1());
                if (img7 != null) {
                    Bitmap bmp7 = BitmapFactory.decodeByteArray(img7, 0, img7.length);
                    mImageIds[0] = bmp7;
                }
                byte img8[] = openDatabase("fclothesdress.db","fclothesdtable",contact.getClothId2());
                if (img8!=null) {
                    Bitmap bmp8 = BitmapFactory.decodeByteArray(img8, 0, img8.length);
                    mImageIds[1] = bmp8;
                }
            }
        }

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(mContext);
            if(mImageIds[i]!=null)
                imageView.setImageBitmap(mImageIds[i]);
            else{
                imageView.setImageResource(R.drawable.no_image);
            }
            imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            return imageView;
        }
    }
}