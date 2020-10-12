package com.example.pebbles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pebbles.Adapters.INUAdapter;
import com.example.pebbles.Adapters.NIUAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp_not_urg);

        tasksRecyclerView = findViewById(R.id.inuList);
        minuAddTaskBtn = findViewById(R.id.inuAddTaskBtn);

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new INUAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

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
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks("inuTable");
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}