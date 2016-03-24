package com.closet.anusha.dressedup;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.mainTv1);
        ImageView imageView = (ImageView) findViewById(R.id.mainScreenImage);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Billabong.ttf");
        textView.setTypeface(face);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainScreen.class);
                startActivity(intent);
            }
        });
    }
}
