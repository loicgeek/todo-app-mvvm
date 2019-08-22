package com.loicngou.todoapp.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.loicngou.todoapp.entities.Todo;
import com.loicngou.todoapp.repositories.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository repository ;
    private LiveData<List<Todo>> allTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TodoRepository(application);
        this.allTodos = this.repository.getAllTodos();
    }

    public void insert(Todo todo){
        repository.insert(todo);
    }

    public void update(Todo todo){
        repository.update(todo);
    }

    public void delete(Todo todo){
        repository.delete(todo);
    }

    public void deleteAllTodo(){
        repository.deleteAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos(){
        return  this.allTodos;
    }
}
