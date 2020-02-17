package com.example.note.dao;

import com.example.note.model.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotebookRepo extends JpaRepository<Notebook, UUID> {
    List<Notebook> findAll();
}
