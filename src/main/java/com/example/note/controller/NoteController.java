package com.example.note.controller;

import com.example.note.component.Mapper;
import com.example.note.model.Note;
import com.example.note.dao.NoteRepo;
import com.example.note.dao.NotebookRepo;
import com.example.note.viewModel.NoteViewModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin
public class NoteController {
    private NoteRepo noteRepo;
    private NotebookRepo notebookRepo;
    private Mapper mapper;

    public NoteController(NoteRepo noteRepo, NotebookRepo notebookRepo, Mapper mapper) {
        this.noteRepo = noteRepo;
        this.notebookRepo = notebookRepo;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public List<NoteViewModel> all(){
        var notes = this.noteRepo.findAll();
        List<NoteViewModel>  noteViewModels = notes.stream()
                                  .map(note -> this.mapper.convertToNoteViewModel(note))
                                  .collect(Collectors.toList());
        return noteViewModels;
    }


    @GetMapping("/byId/{id}")
    public NoteViewModel byId(@PathVariable String id){
        var note = this.noteRepo.findById(UUID.fromString(id)).orElse(null);

        if(note == null){
            throw  new EntityNotFoundException();
        }

        var noteViewModel = this.mapper.convertToNoteViewModel(note);
        return noteViewModel;
    }

    @GetMapping("/byNotebook/{notebookId}")
    public List<NoteViewModel> byNotebook(@PathVariable String notebookId){
        List<Note> notes = new ArrayList<>();

        var notebook = this.notebookRepo.findById(UUID.fromString(notebookId));
        if(notebook.isPresent()){
            notes = this.noteRepo.findAllByNotebook(notebook.get());
        }

        var notesViewModel = notes.stream()
                                  .map(note -> this.mapper.convertToNoteViewModel(note))
                                  .collect(Collectors.toList());
        return notesViewModel;
    }

    @PostMapping
    public Note save(@RequestBody NoteViewModel noteCreateViewModel, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        var note = this.mapper.convertToNoteEntity(noteCreateViewModel);

        this.noteRepo.save(note);
        return note;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        this.noteRepo.deleteById(UUID.fromString(id));
    }
}
