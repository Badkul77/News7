package com.example.news7;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity
{
    private ImageView logo;
    TextView name;
    Animation anim1,anim2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news7splash);
        logo=findViewById(R.id.splashlogo);
        name=findViewById(R.id.name);
        anim1= AnimationUtils.loadAnimation(this,R.anim.mytransition);
        logo.setAnimation(anim1);
        anim2=AnimationUtils.loadAnimation(this,R.anim.frombottom);
        name.setAnimation(anim2);
       getSupportActionBar().hide();
       new MyThread().start();
    }
    class MyThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            try {
                Thread.sleep(5000);
                Intent in=new Intent(Splash.this,HomeActivity.class);
                startActivity(in);
                finish();
            }
            catch (Exception e)
            {

            }
        }
    }
}
