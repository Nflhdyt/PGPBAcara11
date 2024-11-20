package com.example.mynote;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteTitle, noteDescription, noteDate;
    private Button addButton, updateButton, deleteButton;
    private NoteRoomDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Inisialisasi view
        noteTitle = findViewById(R.id.noteTitle);
        noteDescription = findViewById(R.id.noteDescription);
        noteDate = findViewById(R.id.noteDate);
        addButton = findViewById(R.id.btn_add);
        updateButton = findViewById(R.id.btn_update);
        deleteButton = findViewById(R.id.btn_delete);

        noteDatabase = NoteRoomDatabase.getDatabase(this);

        // Tambahkan note
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteTitle.getText().toString().trim();
                String desc = noteDescription.getText().toString().trim();
                String date = noteDate.getText().toString().trim();

                if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
                    Toast.makeText(AddNoteActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    Note note = new Note(title, desc, date);

                    new Thread(() -> {
                        noteDatabase.noteDao().insert(note);
                        runOnUiThread(() -> {
                            Toast.makeText(AddNoteActivity.this, "Note added", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }).start();
                }
            }
        });

                // Update note (belum diimplementasikan)
        updateButton.setOnClickListener(v ->
                Toast.makeText(AddNoteActivity.this, "Update feature not implemented yet", Toast.LENGTH_SHORT).show()
        );

        // Delete note (belum diimplementasikan)
        deleteButton.setOnClickListener(v ->
                Toast.makeText(AddNoteActivity.this, "Delete feature not implemented yet", Toast.LENGTH_SHORT).show()
        );
    }
}
