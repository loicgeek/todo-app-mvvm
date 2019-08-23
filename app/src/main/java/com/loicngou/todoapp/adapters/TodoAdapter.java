package com.loicngou.todoapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.loicngou.todoapp.R;
import com.loicngou.todoapp.entities.Todo;



public class TodoAdapter extends ListAdapter<Todo,TodoAdapter.TodoHolder> {

    private OnItemClickListener listener;

    public TodoAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Todo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Todo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription())&&
                    oldItem.getPriority()==newItem.getPriority() &&
                    oldItem.getDone().equals(newItem.getDone());
        }
    };

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item,parent,false);

        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Todo currentTodo = getItem(position);
        holder.titleTextView.setText(currentTodo.getTitle());
        holder.descriptionTextView.setText(currentTodo.getDescription());
        holder.priorityTextView.setText(String.valueOf(currentTodo.getPriority()));
        holder.stateImageView.setImageResource(currentTodo.getDone() ? R.drawable.ic_check:R.drawable.ic_close);
    }

    public Todo getTodoAt(int position){
        return getItem(position);
    }

    class TodoHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView priorityTextView;
        private ImageView stateImageView;

        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            priorityTextView = itemView.findViewById(R.id.priority_text_view);
            stateImageView = itemView.findViewById(R.id.state_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener!= null && position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
         void onItemClick(Todo todo);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
