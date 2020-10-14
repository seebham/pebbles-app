package com.example.pebbles;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout miuBox, mniuBox, minuBox, mninuBox, miuProg, mniuProg, minuProg, mninuProg;
    private TextView miuStat, mniuStat, minuStat, mninuStat;

    SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    public static final String mypreference = "mypref";
    public static final String iuCount = "iuCount";
    public static final String niuCount = "niuCount";
    public static final String inuCount = "inuCount";
    public static final String ninuCount = "ninuCount";
    public static final String iuTotal = "iuTotal";
    public static final String niuTotal = "niuTotal";
    public static final String inuTotal = "inuTotal";
    public static final String ninuTotal = "ninuTotal";
    public static final String iuPercent = "iuPrecent";
    public static final String niuPercent = "niuPrecent";
    public static final String inuPercent = "inuPrecent";
    public static final String ninuPercent = "ninuPrecent";
    public static final String fullBarWidth = "fullBarWidth";

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

        miuStat = findViewById(R.id.iuStat);
        mniuStat = findViewById(R.id.niuStat);
        minuStat = findViewById(R.id.inuStat);
        mninuStat = findViewById(R.id.ninuStat);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        if(!sharedpreferences.contains(fullBarWidth)){
            ViewTreeObserver observer= miuProg.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            Log.d("Kand", "Width: " + miuProg.getWidth());

                            editor.putInt(fullBarWidth, miuProg.getWidth());
                            editor.apply();
                            Log.d("kand", String.valueOf(sharedpreferences.getInt(fullBarWidth, 404)));
                            miuProg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
        } else {
            setBars();
        }

        SimpleAnimation();

        miuBox.setOnClickListener(this);
        mniuBox.setOnClickListener(this);
        minuBox.setOnClickListener(this);
        mninuBox.setOnClickListener(this);

        sharedPrefChangedListen();
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

    public void info(View view){
        System.out.println("Kand" + miuBox.getWidth());
    }


    public void setStats(String count, String totalCount, TextView t, FrameLayout frameBox, String percent){
        Log.d("kand in setStats", String.valueOf(sharedpreferences.getInt(fullBarWidth, 404)));
        boxFullWidth = sharedpreferences.getInt(fullBarWidth, 0);
        //boxFullWidth = 972;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) frameBox.getLayoutParams();

        boolean dataAvailable = false;
        if(sharedpreferences.contains(percent)){
            if(sharedpreferences.contains(totalCount)){
                if(sharedpreferences.getInt(totalCount, 0) != 0) {
                    dataAvailable = true;
                    t.setText(sharedpreferences.getInt(count, 0) + " of " + sharedpreferences.getInt(totalCount, 0) + " Completed");
                    params.width = sharedpreferences.getInt(percent, boxFullWidth);
                }
            }
        }
        if(!dataAvailable){
            t.setText("No pebbles here");
            params.width = boxFullWidth;
        }
        frameBox.setLayoutParams(params);
        frameBox.requestLayout();
    }

    private void setBars(){
        setStats(iuCount, iuTotal, miuStat, miuProg, iuPercent);
        setStats(niuCount, niuTotal, mniuStat, mniuProg, niuPercent);
        setStats(inuCount, inuTotal, minuStat, minuProg, inuPercent);
        setStats(ninuCount, ninuTotal, mninuStat, mninuProg, ninuPercent);
    }

    private void sharedPrefChangedListen(){
        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                System.out.println("kand: MainActivity - " + key);
                switch (key){
                    case "iuPercent":
                        setStats(iuCount, iuTotal, miuStat, miuProg, iuPercent);
                        break;
                    case "niuPercent":
                        setStats(niuCount, niuTotal, mniuStat, mniuProg, niuPercent);
                        break;
                    case "inuPercent":
                        setStats(inuCount, inuTotal, minuStat, minuProg, inuPercent);
                        break;
                    case "ninuPercent":
                        setStats(ninuCount, ninuTotal, mninuStat, mninuProg, ninuPercent);
                        break;
                }
            }
        };
        sharedpreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
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
        if(sharedpreferences.contains(fullBarWidth)) setBars();
    }
}