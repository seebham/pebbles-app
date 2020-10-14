package com.example.pebbles.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pebbles.Model.ToDoModel;
import com.example.pebbles.NotImgUrg;
import com.example.pebbles.NotImpNotUrg;
import com.example.pebbles.R;
import com.example.pebbles.Utils.DatabaseHandler;

import java.util.List;

public class NINUAdapter extends RecyclerView.Adapter<NINUAdapter.ViewHolder> {
    private List<ToDoModel> todoList;
    private NotImpNotUrg activity;
    private DatabaseHandler db;
    private static final String tableName = "ninu";

    public NINUAdapter(DatabaseHandler db, NotImpNotUrg activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db.openDatabase();
        final ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(), 1, tableName);
                } else {
                    db.updateStatus(item.getId(), 0, tableName);
                }
            }
        });
    }

    public int getItemCount(){
        return todoList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTasks(List<ToDoModel> todoList){
        this.todoList = todoList;
//        Toast.makeText(activity, "setTask() Called!", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    public Context getContext() {
        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId(), item.getStatus(), tableName);
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckbox);
        }
    }
}
