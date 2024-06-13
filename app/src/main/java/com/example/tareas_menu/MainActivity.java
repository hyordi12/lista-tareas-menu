package com.example.tareas_menu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListener {
    private List<String> tasks = new ArrayList<>();
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private EditText editTextTask;
    private Button buttonAddTask;


    private static final int MENU_EDIT = 1;
    private static final int MENU_DELETE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        taskAdapter = new TaskAdapter(this, tasks, this);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);

        registerForContextMenu(recyclerViewTasks);

        buttonAddTask.setOnClickListener(v -> {
            String task = editTextTask.getText().toString();
            if (!task.isEmpty()) {
                tasks.add(task);
                taskAdapter.notifyItemInserted(tasks.size() - 1);
                editTextTask.setText("");
            }
        });
    }

    @Override
    public void onEditClick(int position) {
        editTask(position);
    }

    @Override
    public void onDeleteClick(int position) {
        deleteTask(position);
    }

    private void editTask(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Tarea");

        final EditText editText = new EditText(this);
        editText.setText(tasks.get(position));
        builder.setView(editText);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String editedTask = editText.getText().toString().trim();
            if (!editedTask.isEmpty()) {
                tasks.set(position, editedTask);
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Tarea editada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Por favor, introduce un texto válido", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show(); // Muestra el diálogo
    }

    private void deleteTask(int position) {
        tasks.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
}
