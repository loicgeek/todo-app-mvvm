package com.loicngou.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddEditTodoActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.loicngou.todoapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.loicngou.todoapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.loicngou.todoapp.EXTRA_PRIORITY";
    public static final String EXTRA_ID =
            "com.loicngou.todoapp.EXTRA_ID";

    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private NumberPicker priorityNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        titleEditText = findViewById(R.id.title_edit_text);
        descriptionEditText = findViewById(R.id.descriptiion_edit_text);
        priorityNumberPicker = findViewById(R.id.priority_number_picker);
        priorityNumberPicker.setMinValue(1);
        priorityNumberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent i = getIntent();
        if(i.hasExtra(EXTRA_ID)){
            setTitle("Edit Todo");
            titleEditText.setText(i.getStringExtra(AddEditTodoActivity.EXTRA_TITLE));
            descriptionEditText.setText(i.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION));
            priorityNumberPicker.setValue(i.getIntExtra(AddEditTodoActivity.EXTRA_PRIORITY,1));
        }else{
            setTitle("Add Todo");
        }
    }

    public void saveTodo(){
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int priority = priorityNumberPicker.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty() ){
            Toast.makeText(this,"Please Fill all Field",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        if(getIntent().hasExtra(EXTRA_ID)) {
            int id = getIntent().getIntExtra(AddEditTodoActivity.EXTRA_ID,-1);
            data.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_todo_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_todo :
                saveTodo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
