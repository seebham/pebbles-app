package com.example.pebbles.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pebbles.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;

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

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String iuCount = "iuCount";
    public static final String niuCount = "niuCount";
    public static final String inuCount = "inuCount";
    public static final String ninuCount = "ninuCount";
    public static final String iuTotal = "iuTotal";
    public static final String niuTotal = "niuTotal";
    public static final String inuTotal = "inuTotal";
    public static final String ninuTotal = "ninuTotal";
    public static final String iuPercent = "iuPrecent";
    public static final String niuPercent = "niuPrecent";
    public static final String inuPercent = "inuPrecent";
    public static final String ninuPercent = "ninuPrecent";
    public static final String fullBarWidth = "fullBarWidth";

    private SharedPreferences.Editor editor;
//    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    private String tableCreateQueries(String tableName){
        return "CREATE TABLE " + tableName + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";
    }

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
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
                setPreferences("inc", iuTotal);
                break;
            case "niu":
                db.insert(NIU_TABLE, null, cv);
                setPreferences("inc", niuTotal);
                break;
            case "inu":
                db.insert(INU_TABLE, null, cv);
                setPreferences("inc", inuTotal);
                break;
            case "ninu":
                db.insert(NINU_TABLE, null, cv);
                setPreferences("inc", ninuTotal);
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
        String statusMode;
        statusMode = status == 0 ? "dec" : "inc";
//        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
        switch (tableName){
            case "iu":
                db.update(IU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                setPreferences(statusMode, iuCount);
                break;
            case "niu":
                db.update(NIU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                setPreferences(statusMode, niuCount);
                break;
            case "inu":
                db.update(INU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                setPreferences(statusMode, inuCount);
                break;
            case "ninu":
                db.update(NINU_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
                setPreferences(statusMode, ninuCount);
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

    public void deleteTask(int id, int status, String tableName){
//        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
        switch (tableName){
            case "iu":
                db.delete(IU_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
                if(status == 1) setPreferences("dec", iuCount);
                setPreferences("dec", iuTotal);
                break;
            case "niu":
                db.delete(NIU_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
                if(status == 1) setPreferences("dec", niuCount);
                setPreferences("dec", niuTotal);
                break;
            case "inu":
                db.delete(INU_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
                if(status == 1) setPreferences("dec", inuCount);
                setPreferences("dec", inuTotal);
                break;
            case "ninu":
                db.delete(NINU_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
                if(status == 1) setPreferences("dec", ninuCount);
                setPreferences("dec", ninuTotal);
                break;
        }
    }

    public void setPreferences(String mode, String key){
        sharedpreferences = context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        System.out.println("kand: Sample = " + sharedpreferences.getString("sample", "0"));
        System.out.println("kand: " + sharedpreferences.getInt(key, 0));
        switch (mode){
            case "inc":
                System.out.println("kand: inc called");
                if(sharedpreferences.contains(key)){
                    System.out.println("kand: inc key found");
                    editor.putInt(key, sharedpreferences.getInt(key, 0) + 1);
                } else {
                    editor.putInt(key, 1);
                }
                editor.apply();
                break;
            case "dec":
                if(sharedpreferences.contains(key)){
                    editor.putInt(key, sharedpreferences.getInt(key, 0) - 1);
                } else {
                    editor.putInt(key, 0);
                }
                editor.apply();
                break;
        }
        setProgressWidths();
    }

    public void setProgressWidths(){
//        System.out.println("kand: dbHandle: setProgressWidths() called");
        int fullWidth = sharedpreferences.getInt(fullBarWidth, 972);
//        int fullWidth = 972;
//        System.out.println("kand: " + 1/1.0 * fullWidth);
        sharedpreferences = context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putInt(iuPercent, (int)(sharedpreferences.getInt(iuCount, 0)/(double)sharedpreferences.getInt(iuTotal, 1) * fullWidth));
        editor.apply();
        editor.putInt(niuPercent, (int)(sharedpreferences.getInt(niuCount, 0)/(double)sharedpreferences.getInt(niuTotal, 1) * fullWidth));
        editor.apply();
        editor.putInt(inuPercent, (int)(sharedpreferences.getInt(inuCount, 0)/(double)sharedpreferences.getInt(inuTotal, 1) * fullWidth));
        editor.apply();
        editor.putInt(ninuPercent, (int)(sharedpreferences.getInt(ninuCount, 0)/(double)sharedpreferences.getInt(ninuTotal, 1) * fullWidth));
        editor.apply();
//      Formula: SpecificWidth = (completedTasksCount / totalTasksCount * 100) * fullWidth / 100
//                             = completedTasksCount / (double) totalTasksCount * fullWidth
    }
}
