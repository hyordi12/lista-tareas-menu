package com.example.tareas_menu;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<String> tasks;
    private Context context;
    private OnItemClickListener onItemClickListener;


    private static final int MENU_EDIT = 1;
    private static final int MENU_DELETE = 2;

    public TaskAdapter(Context context, List<String> tasks, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.tasks = tasks;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView textViewTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(String task) {
            textViewTask.setText(task);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opciones");
            MenuItem editItem = menu.add(Menu.NONE, MENU_EDIT, Menu.NONE, "Editar");
            MenuItem deleteItem = menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Eliminar");


            editItem.setOnMenuItemClickListener(item -> {
                onItemClickListener.onEditClick(getAdapterPosition());
                return true;
            });
            deleteItem.setOnMenuItemClickListener(item -> {
                onItemClickListener.onDeleteClick(getAdapterPosition());
                return true;
            });
        }
    }

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}
