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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebbles.Adapters.INUAdapter;
import com.example.pebbles.Model.ToDoModel;
import com.example.pebbles.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImpNotUrg extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksRecyclerView;
    private INUAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private Button minuAddTaskBtn;
    private TextView minuStatD;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String inuCount = "inuCount";
    public static final String inuTotal = "inuTotal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp_not_urg);

        tasksRecyclerView = findViewById(R.id.inuList);
        minuAddTaskBtn = findViewById(R.id.inuAddTaskBtn);
        minuStatD = findViewById(R.id.inuStatD);

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new INUAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        setStats();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks("inuTable");
        System.out.println("kaand:" + taskList);
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        minuAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance("inu").show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                System.out.println("kand: inu - " + key);
                if(key.equals("inuCount") || key.equals("inuTotal")){
                    setStats();
                }
            }
        };
        sharedpreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    private void setStats(){
        if(sharedpreferences.contains(inuCount) || sharedpreferences.contains(inuTotal)){
            System.out.println("kand: niu -  setStats, Percent = " + sharedpreferences.getInt("niuPercent", 404));
            if(sharedpreferences.getInt(inuTotal, 0) > 0) minuStatD.setText(sharedpreferences.getInt(inuCount, 0) + " of " + sharedpreferences.getInt(inuTotal, 0) + " completed");
            else minuStatD.setText(R.string.addTasks);
        } else {
            minuStatD.setText(R.string.addTasks);
        }
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks("inuTable");
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}