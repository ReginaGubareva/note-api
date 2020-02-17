package com.example.note.component;

import com.example.note.dao.NotebookRepo;
import com.example.note.model.Note;
import com.example.note.model.Notebook;
import com.example.note.viewModel.NoteViewModel;
import com.example.note.viewModel.NotebookViewModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Mapper {
    private NotebookRepo notebookRepo;

    public Mapper(NotebookRepo notebookRepo) {
        this.notebookRepo = notebookRepo;
    }

    public NoteViewModel convertToNoteViewModel(Note noteEntity){
        var viewModel = new NoteViewModel();
        viewModel.setId(noteEntity.getId().toString());
        viewModel.setTitle(noteEntity.getTitle());
        viewModel.setText(noteEntity.getText());
        viewModel.setLastModifiedOn(noteEntity.getLastModifiedOn());
        viewModel.setNotebookId(noteEntity.getNotebookId());
        return viewModel;
    }

    public Note convertToNoteEntity(NoteViewModel viewModel){
        var notebook = this.notebookRepo.findById(UUID.fromString(viewModel.getNotebookId())).get();
        var entity = new Note(viewModel.getId(), viewModel.getTitle(), viewModel.getText(), notebook);
        return entity;
    }

    public NotebookViewModel convertToNotebookViewModel(Notebook entity){
        var viewModel = new NotebookViewModel();
        viewModel.setId(entity.getId().toString());
        viewModel.setName(entity.getName());
        viewModel.setNbNotes(entity.getNotes().size());
        return viewModel;
    }

    public Notebook convertToNotebookEntity(NotebookViewModel viewModel) {
        var entity = new Notebook(viewModel.getId(), viewModel.getName());
        return entity;
    }
}
