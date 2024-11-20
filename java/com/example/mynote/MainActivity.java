package com.example.mynote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private NotesAdapter notesAdapter;
    private List<Note> notesList;
    private NoteRoomDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi RecyclerView dan FloatingActionButton
        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);

        // Inisialisasi daftar catatan dan adapter
        notesList = new ArrayList<>();
        notesAdapter = new NotesAdapter(notesList);

        // Inisialisasi database Room
        noteDatabase = NoteRoomDatabase.getDatabase(this);

        // Konfigurasi RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notesAdapter);

        // Muat data awal dari database
        loadNotes();

        // Navigasi ke AddNoteActivity
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });
    }

    private void loadNotes() {
        new Thread(() -> {
            // Ambil data dari database
            notesList.clear();
            notesList.addAll(noteDatabase.noteDao().getAllNotes().getValue()); // Sesuaikan DAO untuk List<Note>

            // Perbarui tampilan RecyclerView di UI thread
            runOnUiThread(() -> notesAdapter.notifyDataSetChanged());
        }).start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Muat ulang data saat kembali ke aktivitas ini
        loadNotes();
    }
}
