package com.example.pebbles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pebbles.Adapter.ToDoAdapter;
import com.example.pebbles.Model.ToDoModel;
import com.example.pebbles.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ImpUrg extends AppCompatActivity implements DialogCloseListener{
    private FrameLayout miuBoxD;
    private TextView miuTextD, miuStatD;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private Button miuAddTaskBtn;

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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = new ArrayList<>();

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        //Starting Game Dummy Inputs
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

        taskList = db.getAllTasks();
        System.out.println("kaand:" + taskList);
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        miuAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    public void goBack(){
        Intent intent = new Intent(this, MainActivity.class);
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(miuBoxD, "iuBoxT");
        pairs[1] = new Pair<View, String>(miuTextD, "iuTextT");
        pairs[2] = new Pair<View, String>(miuStatD, "iuStatT");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}