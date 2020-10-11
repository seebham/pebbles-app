package com.example.pebbles.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pebbles.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";

//    private static final String TODO_TABLE = "todo";
    private static final String IU_TABLE = "iuTable";
    private static final String NIU_TABLE = "niuTable";
    private static final String INU_TABLE = "inuTable";
    private static final String NINU_TABLE = "ninuTable";

    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";

//    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    private String tableCreateQueries(String tableName){
        return "CREATE TABLE " + tableName + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";
    }

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(tableCreateQueries(IU_TABLE));
        db.execSQL(tableCreateQueries(NIU_TABLE));
        db.execSQL(tableCreateQueries(INU_TABLE));
        db.execSQL(tableCreateQueries(NINU_TABLE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + IU_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NIU_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INU_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NINU_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task, String table){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
//        db.insert(TODO_TABLE, null, cv);
        switch (table){
            case "iu":
                db.insert(IU_TABLE, null, cv);
                break;
            case "niu":
                db.insert(NIU_TABLE, null, cv);
                break;
            case "inu":
                db.insert(INU_TABLE, null, cv);
                break;
            case "ninu":
                db.insert(NINU_TABLE, null, cv);
                break;
        }
    }

    public List<ToDoModel> getAllTasks(String exactTableName){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(exactTableName, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status, String tableName){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
//        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
        switch (tableName){
            case "iu":
                db.update(IU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                break;
            case "niu":
                db.update(NIU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                break;
            case "inu":
                db.update(INU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                break;
            case "ninu":
                db.update(NINU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                break;
        }
    }

    public void updateTask(int id, String task, String tableName) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
//        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
        switch (tableName){
            case "iu":
                db.update(IU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                break;
            case "niu":
                db.update(NIU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                break;
            case "inu":
                db.update(INU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                break;
            case "ninu":
                db.update(NINU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                break;
        }
    }

    public void deleteTask(int id, String tableName){
//        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
        switch (tableName){
            case "iu":
                db.delete(IU_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
                break;
            case "niu":
                db.delete(NIU_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
                break;
            case "inu":
                db.delete(INU_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
                break;
            case "ninu":
                db.delete(NINU_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
                break;
        }
    }
}
