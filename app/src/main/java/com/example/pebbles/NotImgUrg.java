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

import com.example.pebbles.Adapters.NIUAdapter;
import com.example.pebbles.Model.ToDoModel;
import com.example.pebbles.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotImgUrg extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksRecyclerView;
    private NIUAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private Button mniuAddTaskBtn;
    private TextView mniuStatD;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String niuCount = "niuCount";
    public static final String niuTotal = "niuTotal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_img_urg);

        tasksRecyclerView = findViewById(R.id.niuList);
        mniuAddTaskBtn = findViewById(R.id.niuAddTaskBtn);
        mniuStatD = findViewById(R.id.niuStatD);

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new NIUAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        setStats();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks("niuTable");
        System.out.println("kand: niu - " + taskList);
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        mniuAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance("niu").show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                System.out.println("kand: niu - " + key);
                if(key.equals("niuCount") || key.equals("niuTotal")){
                    setStats();
                }
            }
        };
        sharedpreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    private void setStats(){
        if(sharedpreferences.contains(niuCount) || sharedpreferences.contains(niuTotal)){
            System.out.println("kand: niu -  setStats, Percent = " + sharedpreferences.getInt("niuPercent", 404));
            if(sharedpreferences.getInt(niuTotal, 0) > 0) mniuStatD.setText(sharedpreferences.getInt(niuCount, 0) + " of " + sharedpreferences.getInt(niuTotal, 0) + " completed");
            else mniuStatD.setText(R.string.addTasks);
        } else {
            mniuStatD.setText(R.string.addTasks);
        }
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks("niuTable");
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}