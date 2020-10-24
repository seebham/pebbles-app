package com.example.pebbles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pebbles.Adapters.NINUAdapter;
import com.example.pebbles.Model.ToDoModel;
import com.example.pebbles.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotImpNotUrg extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksRecyclerView;
    private NINUAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private Button mninuAddTaskBtn;
    private TextView mninuStatD;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String ninuCount = "ninuCount";
    public static final String ninuTotal = "ninuTotal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_imp_not_urg);

        tasksRecyclerView = findViewById(R.id.ninuList);
        mninuAddTaskBtn = findViewById(R.id.ninuAddTaskBtn);
        mninuStatD = findViewById(R.id.ninuStatD);

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new NINUAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        setStats();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks("ninuTable");
        System.out.println("kaand:" + taskList);
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        mninuAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance("ninu").show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                System.out.println("kand: ninu - " + key);
                if(key.equals("ninuCount") || key.equals("ninuTotal")){
                    setStats();
                }
            }
        };
        sharedpreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    private void setStats(){
        if(sharedpreferences.contains(ninuCount) || sharedpreferences.contains(ninuTotal)){
            System.out.println("kand: ninu -  setStats, Percent = " + sharedpreferences.getInt("ninuPercent", 404));
            if(sharedpreferences.getInt(ninuTotal, 0) > 0) mninuStatD.setText(sharedpreferences.getInt(ninuCount, 0) + " of " + sharedpreferences.getInt(ninuTotal, 0) + " completed");
            else mninuStatD.setText(R.string.addTasks);
        } else {
            mninuStatD.setText(R.string.addTasks);
        }
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks("ninuTable");
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}