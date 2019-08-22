package com.loicngou.todoapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.loicngou.todoapp.TodoDatabase;
import com.loicngou.todoapp.daos.TodoDao;
import com.loicngou.todoapp.entities.Todo;

import java.util.List;

public class TodoRepository {

    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;

    public TodoRepository(Application application){
        TodoDatabase todoDatabase = TodoDatabase.getInstance(application);
        todoDao = todoDatabase.todoDao();
        allTodos = todoDao.getAllTodo();
    }

    public void insert(Todo todo){
        new AsyncInsertTodo(this.todoDao).execute(todo);
    }

    public void update(Todo todo){
       new AsyncUpdateTodo(this.todoDao).execute(todo);
    }

    public void delete (Todo todo){
        new AsyncDeleteTodo(this.todoDao).execute(todo);
    }

    public void deleteAllTodos (){
        new AsyncDeleteAllTodo(this.todoDao).execute();
    }

    public LiveData<List<Todo>> getAllTodos(){
        return this.allTodos;
    }

    private static class AsyncInsertTodo extends AsyncTask<Todo,Void,Void>{

        private TodoDao todoDao;

        AsyncInsertTodo(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
             todoDao.insert(todos[0]);
             return null;
        }
    }

    private static class AsyncUpdateTodo extends AsyncTask<Todo,Void,Void>{

        private TodoDao todoDao;

        AsyncUpdateTodo(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.update(todos[0]);
            return null;
        }
    }

    private static class AsyncDeleteTodo extends AsyncTask<Todo,Void,Void>{

        private TodoDao todoDao;

        AsyncDeleteTodo(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.delete(todos[0]);
            return null;
        }
    }

    private static class AsyncDeleteAllTodo extends AsyncTask<Void,Void,Void>{

        private TodoDao todoDao;

        AsyncDeleteAllTodo(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.deleteAllTodo();
            return null;
        }
    }
}
