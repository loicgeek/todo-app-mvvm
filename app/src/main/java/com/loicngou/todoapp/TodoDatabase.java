package com.loicngou.todoapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.loicngou.todoapp.entities.Todo;
import com.loicngou.todoapp.daos.TodoDao;

import java.util.Date;

@Database(entities = {Todo.class},version=4)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase instance = null;
    public abstract TodoDao todoDao();

    public static synchronized TodoDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),TodoDatabase.class,"todo_app_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new  RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private TodoDao todoDao;

        PopulateDbAsyncTask(TodoDatabase todoDatabase){
            this.todoDao = todoDatabase.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.todoDao.insert(new Todo("manger","Je dois Manger Pour etre en bonne sante",1));
            this.todoDao.insert(new Todo("courir","je dois aller courir au stade cicam",2));
            return null;
        }
    }
}
