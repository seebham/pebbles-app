package com.example.pebbles;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout miuBox, mniuBox, minuBox, mninuBox, miuProg, mniuProg, minuProg, mninuProg;

    private int boxFullWidth, boxHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miuBox = findViewById(R.id.iuBox);
        mniuBox = findViewById(R.id.niuBox);
        minuBox = findViewById(R.id.inuBox);
        mninuBox = findViewById(R.id.ninuBox);

        miuProg = findViewById(R.id.iuProg);
        mniuProg = findViewById(R.id.niuProg);
        minuProg = findViewById(R.id.inuProg);
        mninuProg = findViewById(R.id.ninuProg);


        SimpleAnimation();
//        miuProg.setLayoutParams(new FrameLayout.LayoutParams(miuProg.getWidth()/2,miuProg.getHeight()));



        miuBox.setOnClickListener(this);
        mniuBox.setOnClickListener(this);
        minuBox.setOnClickListener(this);
        mninuBox.setOnClickListener(this);
    }

    private void SimpleAnimation(){
        miuProg.startAnimation(inFromLeftAnimation());
        mniuProg.startAnimation(inFromLeftAnimation());
        minuProg.startAnimation(inFromLeftAnimation());
        mninuProg.startAnimation(inFromLeftAnimation());
    }

    private void ComplexAnimation(){
        AnimatorSet set = new AnimatorSet();
        // Using property animation
        ObjectAnimator aniIU = ObjectAnimator.ofFloat(miuProg,
                "translationX", -1000f, 0f);
        aniIU.setDuration(500);
        ObjectAnimator aniNIU = ObjectAnimator.ofFloat(mniuProg,
                "translationX", -1000f, 0f);
        aniNIU.setDuration(500);
        set.play(aniNIU).after(aniIU);
        ObjectAnimator aniINU = ObjectAnimator.ofFloat(minuProg,
                "translationX", -1000f, 0f);
        aniINU.setDuration(500);
        set.play(aniINU).after(aniNIU);
        ObjectAnimator aniNINU = ObjectAnimator.ofFloat(mninuProg,
                "translationX", -1000f, 0f);
        aniNINU.setDuration(500);
        set.play(aniNINU).after(aniINU);
        set.start();
    }

    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(500);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    public void wow(View view){
        //Box Full Width
        boxFullWidth = miuProg.getWidth();
        boxHeight = miuBox.getHeight();
        miuProg.setLayoutParams(new FrameLayout.LayoutParams(progressWidth(4, 5, boxFullWidth), boxHeight));

//        AnimatorSet set = new AnimatorSet();
//        // Using property animation
//        ObjectAnimator animation = ObjectAnimator.ofFloat(miuProg,
//                "translationY",
//                100f);
//        animation.setDuration(2000);
//        set.play(animation);
//        set.start();

//        Toast.makeText(this, "wow", Toast.LENGTH_SHORT).show();
    }

    public int progressWidth(int completedTasksCount, float totalTasksCount, int fullWidth){
        return (int) ((completedTasksCount / totalTasksCount * 100) * fullWidth / 100);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        Pair[] pairs = new Pair[1];
        ActivityOptions options;
        switch (view.getId()){
            case R.id.iuBox:
                i = new Intent(MainActivity.this, ImpUrg.class);
                pairs[0] = new Pair<View, String>(miuBox, "iuBoxT");
                options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(i, options.toBundle());
                break;
            case R.id.niuBox:
                i = new Intent(MainActivity.this, NotImgUrg.class);
                pairs[0] = new Pair<View, String>(mniuBox, "niuBoxT");
                options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(i, options.toBundle());
                break;
            case R.id.inuBox:
                i = new Intent(MainActivity.this, ImpNotUrg.class);
                pairs[0] = new Pair<View, String>(minuBox, "inuBoxT");
                options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(i, options.toBundle());
                break;
            case R.id.ninuBox:
                i = new Intent(MainActivity.this, NotImpNotUrg.class);
                pairs[0] = new Pair<View, String>(mninuBox, "ninuBoxT");
                options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(i, options.toBundle());
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SimpleAnimation();
    }
}