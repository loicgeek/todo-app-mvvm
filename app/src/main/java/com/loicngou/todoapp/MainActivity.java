package com.loicngou.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loicngou.todoapp.adapters.TodoAdapter;
import com.loicngou.todoapp.entities.Todo;
import com.loicngou.todoapp.viewModels.TodoViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    TodoViewModel todoViewModel;
    final TodoAdapter adapter = new TodoAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FloatingActionButton addTodoButton = findViewById(R.id.add_todo_button);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddEditTodoActivity.class);
                startActivityForResult(i,ADD_NOTE_REQUEST);
            }
        });


        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Intent i = new Intent(MainActivity.this, AddEditTodoActivity.class);
                i.putExtra(AddEditTodoActivity.EXTRA_TITLE,todo.getTitle());
                i.putExtra(AddEditTodoActivity.EXTRA_DESCRIPTION,todo.getDescription());
                i.putExtra(AddEditTodoActivity.EXTRA_ID,todo.getId());
                i.putExtra(AddEditTodoActivity.EXTRA_PRIORITY,todo.getPriority());
                startActivityForResult(i,EDIT_NOTE_REQUEST);
            }
        });

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                Log.i("TODOS",todos.toString());
                adapter.submitList(todos);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Todo todo = adapter.getTodoAt(position);
                if(direction == ItemTouchHelper.RIGHT){
                    todo.setDone(!todo.getDone());
                    todoViewModel.update(todo);
                    adapter.notifyItemChanged(position);
                    Toast.makeText(MainActivity.this, "Todo Updated", Toast.LENGTH_SHORT).show();
                }else{
                    todoViewModel.delete(todo);
                    Toast.makeText(MainActivity.this, "Todo Deleted", Toast.LENGTH_SHORT).show();
                }
                //adapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK){
            String title = data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivity.EXTRA_PRIORITY,-1);

            todoViewModel.insert(new Todo(title,description,priority));
            Toast.makeText(this,"New Todo Inserted",Toast.LENGTH_SHORT).show();
        }else if(requestCode==EDIT_NOTE_REQUEST && resultCode==RESULT_OK){
            String title = data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivity.EXTRA_PRIORITY,-1);
            int id = data.getIntExtra(AddEditTodoActivity.EXTRA_ID,-1);

            Todo todo = new Todo(title,description,priority);
            todo.setId(id);
            todoViewModel.update(todo);
            Log.i("TODO",todo.getTitle());
            Toast.makeText(this,"Todo Updated",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Todo not saved",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear_all_button:
                todoViewModel.deleteAllTodo();
                break;
            case R.id.filter_done_button:
                break;
            case R.id.filter_undone_button:
                break;
            case R.id.filter_reset_button:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return  super.onOptionsItemSelected(item);
    }
}
