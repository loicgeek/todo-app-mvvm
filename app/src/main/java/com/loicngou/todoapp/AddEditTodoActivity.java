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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddEditTodoActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.loicngou.todoapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.loicngou.todoapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE =
            "com.loicngou.todoapp.EXTRA_DATE";
    public static final String EXTRA_ID =
            "com.loicngou.todoapp.EXTRA_DATE";

    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        titleEditText = findViewById(R.id.title_edit_text);
        descriptionEditText = findViewById(R.id.descriptiion_edit_text);
        datePicker = findViewById(R.id.date_picker);
        timePicker = findViewById(R.id.time_picker);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent i = getIntent();
        if(i.hasExtra(EXTRA_ID)){
            setTitle("Edit Todo");
            titleEditText.setText(i.getStringExtra(AddEditTodoActivity.EXTRA_TITLE));
            descriptionEditText.setText(i.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION));
            datePicker.updateDate(2010,2,4);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setMinute(24);
                timePicker.setHour(12);
            }


        }else{
            setTitle("Add Todo");
        }


    }

    public void saveTodo(){
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String date;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            date = String.valueOf(datePicker.getYear()) +"-"
                    +String.valueOf(datePicker.getMonth())+"-"
                    +datePicker.getDayOfMonth() + " "
                    +timePicker.getHour() +":"+timePicker.getMinute();
        }else{
            date = String.valueOf(datePicker.getYear()) +"-"
                    +String.valueOf(datePicker.getMonth())+"-"
                    +datePicker.getDayOfMonth() + " ";
        }

        if(title.trim().isEmpty() || description.trim().isEmpty() ||date.trim().isEmpty()){
            Toast.makeText(this,"Please Fill all Field",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("DATA",date);
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_DATE,date);
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
