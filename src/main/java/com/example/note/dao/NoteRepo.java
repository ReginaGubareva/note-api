package com.example.note.dao;

import com.example.note.model.Note;
import com.example.note.model.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepo extends JpaRepository<Note, UUID> {
    List<Note> findAllByNotebook(Notebook notebook);
    List<Note> findAll();
    Optional<Note> findById(UUID id);
}

