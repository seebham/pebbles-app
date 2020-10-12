package com.example.pebbles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_img_urg);

        tasksRecyclerView = findViewById(R.id.niuList);
        mniuAddTaskBtn = findViewById(R.id.niuAddTaskBtn);

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new NIUAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks("niuTable");
        System.out.println("kaand:" + taskList);
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        mniuAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance("niu").show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks("niuTable");
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}