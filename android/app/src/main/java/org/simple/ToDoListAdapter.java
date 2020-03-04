package org.simple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoItem> {

    public static class ToDoItem extends RecyclerView.ViewHolder {

        private TextView text;

        public ToDoItem(@NonNull View itemView) {
            super(itemView);

            this.text = itemView.findViewById(android.R.id.text1);
        }
    }

    private Context context;

    public ToDoListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new ToDoItem(layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItem holder, int position) {
        holder.text.setText(String.format("to do item %d", position));
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
