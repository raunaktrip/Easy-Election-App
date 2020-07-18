package com.example.easyelectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class launcher extends AppCompatActivity {

    private static int SPLASH_SCREEN=3000;

    Animation topAnim,bottomAnim;
    ImageView image;
    TextView text1,text2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.boottom_anim);

        image=findViewById(R.id.logo);
        text1=findViewById(R.id.textView);
        text2=findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        text1.setAnimation(bottomAnim);
        text2.setAnimation(bottomAnim);

      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              startActivity(new Intent(getApplicationContext(),MainActivity.class));
              finish();
          }
      },SPLASH_SCREEN);

    }
}
