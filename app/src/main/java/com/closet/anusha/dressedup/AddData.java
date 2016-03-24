package com.closet.anusha.dressedup;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class AddData extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    ImageView home,gallery,add,recent,user,imager;
    private Uri mImageCaptureUri;
    int chooser_id=0;
    LinearLayout borrowedLayout;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    RadioGroup radiogroup1,rga,rgc,rgs,borrowedCheck;
    private Button add_details;
    protected Button selectColoursButton, selectTypesButton;
    protected CharSequence[] colours = {"White", "Black", "Blue", "Red", "Green", "Yellow", "Orange", "Pink", "Purple", "Brown", "Grey", "Gold", "Silver", "Others"};
    protected ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();
    protected CharSequence[] dressTypes = {"Daily Wear", "Traditional Wear", "Formal Wear", "Party Wear", "Western Wear", "Special Occasions", "Other"};
    protected ArrayList<CharSequence> selectedTypes = new ArrayList<CharSequence>();
    private RatingBar ratingBar;
    EditText comment,bName;
    TextView textView;
    String LastWornDate=null;
    protected int[] colorsArr;
    protected int[] typesArr;
    protected int rangeArr=0;
    ColorsDatabase myDB_cc;
    TypeDatabase myDB_ct;
    DatePicker dp;
    ClothesDatabase myDB_c;
    AccessoriesDatabase myDB_a;
    ShoeBagDatabase myDB_s;
    int available=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        home = (ImageView) findViewById(R.id.bt_home3);
        gallery = (ImageView) findViewById(R.id.bt_gallery3);
        add = (ImageView) findViewById(R.id.bt_camera3);
        recent = (ImageView) findViewById(R.id.bt_recents3);
        imager = (ImageView) findViewById(R.id.add_image3);
        radiogroup1 = (RadioGroup)findViewById(R.id.chooser);
        rga = (RadioGroup)findViewById(R.id.acc_topbot);
        myDB_cc =  new ColorsDatabase(this);
        myDB_ct =  new TypeDatabase(this);
        textView = (TextView) findViewById(R.id.myImageViewText);
        rgc = (RadioGroup)findViewById(R.id.cloth_topbot);
        rgs = (RadioGroup)findViewById(R.id.shoes_topbot);
        selectColoursButton = (Button) findViewById(R.id.select_colours);
        selectTypesButton = (Button) findViewById(R.id.select_types1);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_c);
        borrowedCheck = (RadioGroup) findViewById(R.id.BorrowedItemCheck);
        add_details = (Button) findViewById(R.id.add_data_c);
        Button dismiss = (Button) findViewById(R.id.dismiss);
        borrowedLayout= (LinearLayout) findViewById(R.id.borrowerLlt);
        dp= (DatePicker) findViewById(R.id.dateReturned);
        comment = (EditText) findViewById(R.id.comments_c);
        bName = (EditText) findViewById(R.id.borrower);
        radiogroup1.setOnCheckedChangeListener(this);
        rga.setOnCheckedChangeListener(this);
        homeClick();
        galleryClick();
        addImage();
        lastWornClick();
        conformationClick(this);

        borrowedCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                available=borrowedCheck.indexOfChild(findViewById(borrowedCheck.getCheckedRadioButtonId()));
                if(available==0){
                    available=3;
                    borrowedLayout.setVisibility(View.VISIBLE);
                }
                else if(available==1){
                    available=0;
                    borrowedLayout.setVisibility(View.VISIBLE);
                }
                else{
                    available=1;
                    borrowedLayout.setVisibility(View.GONE);
                }
            }
        });

        selectColoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.select_colours:
                        showSelectColoursDialog();
                        break;
                    default:
                        break;
                }
            }
        });
        selectTypesButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.select_types1:
                                showSelectTypesDialog();
                                break;
                            default:
                                break;
                        }
                    }
                });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddData.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }

    private void conformationClick(final AddData addData) {
        add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chooser_id==1){
                    rangeArr=rgc.indexOfChild(findViewById(rgc.getCheckedRadioButtonId()));
                    myDB_c =  new ClothesDatabase(addData);
                    if(doesDatabaseExist(addData,"fclothesdress.db")){
                        Toast.makeText(AddData.this, "1",Toast.LENGTH_LONG).show();
                        addDataInfo(1,1);
                    }
                    else{
                        if(doesDatabaseExist(addData,"fcolors.db"))
                            addDataInfo(0,1);
                        else
                            addDataInfo(0,0);
                    }
                }
                else if(chooser_id==2){
                    rangeArr=rga.indexOfChild(findViewById(rga.getCheckedRadioButtonId()));
                    myDB_a =  new AccessoriesDatabase(addData);
                    if(doesDatabaseExist(addData,"faccessories.db")){
                        Toast.makeText(AddData.this, "1",Toast.LENGTH_LONG).show();
                        addDataInfo(1,1);
                    }
                    else{
                        if(doesDatabaseExist(addData,"fcolors.db"))
                            addDataInfo(0,1);
                        else
                            addDataInfo(0,0);
                    }
                }
                else if(chooser_id==3){
                    rangeArr=rgs.indexOfChild(findViewById(rgs.getCheckedRadioButtonId()));
                    myDB_s =  new ShoeBagDatabase(addData);
                    if(doesDatabaseExist(addData,"fshoebag.db")){
                        Toast.makeText(AddData.this, "1",Toast.LENGTH_LONG).show();
                        addDataInfo(1,1);
                    }
                    else{
                        if(doesDatabaseExist(addData,"fcolors.db"))
                            addDataInfo(0,1);
                        else
                            addDataInfo(0,0);
                    }
                }

            }
        });
    }

    public void addDataInfo(final int id,final int color_id) {
        String types=selectedTypes.toString();
        float rating=ratingBar.getRating();
        String colors=selectedColours.toString();
        String comment1=comment.getText().toString();
        if(types==null){
            Toast.makeText(AddData.this, "Please Select Types",Toast.LENGTH_LONG).show();
        }
        if(colors==null){
            Toast.makeText(AddData.this, "Please Select Colors",Toast.LENGTH_LONG).show();
        }
        if(rating==0){
            rating=1;
        }
        boolean inserted = false;
        Bitmap photo = ((BitmapDrawable)imager.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        if(bArray==null){
            Toast.makeText(AddData.this, "Please Select Image",Toast.LENGTH_LONG).show();
        }
        long inserted_color = myDB_cc.insertData(color_id,0,rangeArr,colorsArr);
        long inserted_type = myDB_ct.insertData(color_id,0,typesArr);
        if(available==3 || available==0){
            comment1=bName.getText().toString();
            LastWornDate=dp.getYear()+"-"+dp.getMonth()+"-"+dp.getDayOfMonth();
        }
        if(comment1==null){
            comment1="-";
        }
        if(inserted_color!=-1 && inserted_type!=-1){
            if(chooser_id==1)
                inserted = myDB_c.insertData(id,bArray,(int)inserted_type,rating,LastWornDate,0,(int)inserted_color,comment1,available,rangeArr);
            else if(chooser_id==2)
                inserted = myDB_a.insertData(id,bArray,(int)inserted_type,rating,LastWornDate,0,(int)inserted_color,comment1,available,rangeArr);
            else if(chooser_id==3)
                inserted = myDB_s.insertData(id,bArray,(int)inserted_type,rating,LastWornDate,0,(int)inserted_color,comment1,available,rangeArr);
            if(inserted){
                Toast.makeText(AddData.this, "Data added",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddData.this,MainScreen.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(AddData.this, "Data not added",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(AddData.this, "Data not added",Toast.LENGTH_LONG).show();
        }
    }
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    protected void showSelectColoursDialog() {
        boolean[] checkedColours = new boolean[colours.length];
        int count = colours.length;
        for (int i = 0; i < count; i++)
            checkedColours[i] = selectedColours.contains(colours[i]);
        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    colorsArr[which]=1;
                    selectedColours.add(colours[which]);
                }
                else{
                    colorsArr[which]=0;
                    selectedColours.remove(colours[which]);
                }
                onChangeSelectedColours();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Colours");
        builder.setMultiChoiceItems(colours, checkedColours, coloursDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    protected void onChangeSelectedColours() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence colour : selectedColours)
            stringBuilder.append("-" + colour + "-");
        selectColoursButton.setText(stringBuilder.toString());
    }
    protected void showSelectTypesDialog() {
        boolean[] checkedTypes = new boolean[dressTypes.length];
        int count = dressTypes.length;
        for (int i = 0; i < count; i++)
            checkedTypes[i] = selectedTypes.contains(colours[i]);
        DialogInterface.OnMultiChoiceClickListener typesDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    typesArr[which]=1;
                    selectedTypes.add(dressTypes[which]);
                }
                else {
                    typesArr[which]=0;
                    selectedTypes.remove(dressTypes[which]);
                }
                onChangeSelectedTypes();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Dress Types");
        builder.setMultiChoiceItems(dressTypes, checkedTypes, typesDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    protected void onChangeSelectedTypes() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence type : selectedTypes)
            stringBuilder.append(type + " ");
        selectTypesButton.setText(stringBuilder.toString());
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RelativeLayout rl= (RelativeLayout) findViewById(R.id.rest_rlt);
        rl.setVisibility(View.VISIBLE);
        switch (group.getId()) {
            case R.id.chooser:
                switch (checkedId) {
                    case R.id.clothes_chooser:
                        chooser_id=1;
                        clothesSet();
                        break;
                    case R.id.accessories_chooser:
                        chooser_id=2;
                        accesoriesSet();
                        break;
                    case R.id.shoes_bags_chooser:
                        chooser_id=3;
                        shoesBagSet();
                        break;
                }
                break;
            case R.id.cloth_topbot:
                break;
            case R.id.acc_topbot:
                break;
            case  R.id.shoes_topbot:
                break;
        }
    }

    private void clothesSet() {
        colorsArr = new int[14];
        typesArr = new int[7];
        rgc.setVisibility(View.VISIBLE);
        rga.setVisibility(View.GONE);
        rgs.setVisibility(View.GONE);
    }
    private void accesoriesSet() {
        colorsArr = new int[14];
        typesArr = new int[7];
        rga.setVisibility(View.VISIBLE);
        rgc.setVisibility(View.GONE);
        rgs.setVisibility(View.GONE);
    }
    private void shoesBagSet() {
        colorsArr = new int[14];
        typesArr = new int[7];
        rgs.setVisibility(View.VISIBLE);
        rgc.setVisibility(View.GONE);
        rga.setVisibility(View.GONE);
    }

    private void addImage() {
        imager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.GONE);
                final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddData.this);
                builder.setTitle("Add Image");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                    "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                            try {
                                intent.putExtra("return-data", true);
                                startActivityForResult(intent, PICK_FROM_CAMERA);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (items[item].equals("Choose from Library")) {
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case PICK_FROM_CAMERA:
                doCrop();
                break;
            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();
                doCrop();
                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    imager.setImageBitmap(photo);
                }

                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) f.delete();
                break;
        }
    }

    private void homeClick() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddData.this,MainScreen.class);
                startActivity(intent);
            }
        });
    }
    private void galleryClick() {
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddData.this,GalleryActivity.class);
                startActivity(intent);
            }
        });
    }
    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 150);
            intent.putExtra("outputY", 210);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res	= list.get(0);
                i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();
                    co.title 	= getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon		= getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent= new Intent(intent);
                    co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }
                CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int item ) {
                        startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel( DialogInterface dialog ) {

                        if (mImageCaptureUri != null ) {
                            getContentResolver().delete(mImageCaptureUri, null, null );
                            mImageCaptureUri = null;
                        }
                    }
                } );
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    private void lastWornClick() {
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddData.this,LastWorn.class);
                startActivity(intent);
            }
        });
    }

}
