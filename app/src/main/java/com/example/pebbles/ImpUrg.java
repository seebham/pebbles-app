package com.example.pebbles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.pebbles.Adapters.IUAdapter;
import com.example.pebbles.Model.ToDoModel;
import com.example.pebbles.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImpUrg extends AppCompatActivity implements DialogCloseListener{
    private FrameLayout miuBoxD;
    private TextView miuTextD, miuStatD;
    private RecyclerView tasksRecyclerView;
    private IUAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private Button miuAddTaskBtn;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String iuCount = "iuCount";
    public static final String iuTotal = "iuTotal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp_urg);

        miuBoxD = findViewById(R.id.iuBoxD);
        miuTextD = findViewById(R.id.iuTextD);
        miuStatD = findViewById(R.id.iuStatD);
        tasksRecyclerView = findViewById(R.id.iuList);
        miuAddTaskBtn = findViewById(R.id.iuAddTaskBtn);

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new IUAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        setStats();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        //Starting Dummy List Items
//        ToDoModel task = new ToDoModel();
//        task.setTask("This is our test task");
//        task.setStatus(0);
//        task.setId(1);
//
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//
//        tasksAdapter.setTasks(taskList);

        taskList = db.getAllTasks("iuTable");
        System.out.println("kaand:" + taskList);
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        miuAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance("iu").show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                System.out.println("kand: niu - " + key);
                if(key.equals("iuCount") || key.equals("iuTotal")){
                    setStats();
                }
            }
        };
        sharedpreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    private void setStats(){
        if(sharedpreferences.contains(iuCount) || sharedpreferences.contains(iuTotal)){
            System.out.println("kand: iu -  setStats, Percent = " + sharedpreferences.getInt("iuPercent", 404));
            miuStatD.setText(sharedpreferences.getInt(iuCount, 0) + " of " + sharedpreferences.getInt(iuTotal, 0) + " completed");
        } else {
            miuStatD.setText("Add New Tasks!");
        }
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks("iuTable");
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}