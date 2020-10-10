package com.example.pebbles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FrameLayout miuBox;
    private TextView miuText, miuStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miuBox = findViewById(R.id.iuBox);
        miuText = findViewById(R.id.iuText);
        miuStat = findViewById(R.id.iuStat);
        miuBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImpUrg.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(miuBox, "iuBoxT");
//                pairs[1] = new Pair<View, String>(miuText, "iuTextT");
//                pairs[2] = new Pair<View, String>(miuStat, "iuStatT");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, options.toBundle());
//                String width = String.valueOf(mFrameLayout.getWidth());
//                Toast.makeText(MainActivity.this, width, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void wow(View view){

        Toast.makeText(this, "wow", Toast.LENGTH_SHORT).show();
    }
}