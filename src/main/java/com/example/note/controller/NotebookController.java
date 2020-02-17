package com.example.note.controller;

import com.example.note.component.Mapper;
import com.example.note.dao.NotebookRepo;
import com.example.note.model.Notebook;
import com.example.note.viewModel.NotebookViewModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/notebooks")
@CrossOrigin
public class NotebookController {
    private NotebookRepo notebookRepo;
    private Mapper mapper;

    public NotebookController(NotebookRepo notebookRepo, Mapper mapper){
        this.notebookRepo = notebookRepo;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public List<Notebook> all(){
        List<Notebook> allNotebook = this.notebookRepo.findAll();
        return allNotebook;
    }

    @PostMapping()
    public Notebook save(@RequestBody NotebookViewModel notebookViewModel,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw  new ValidationException();
        }

        var notebookEntity = this.mapper.convertToNotebookEntity(notebookViewModel);
        this.notebookRepo.save(notebookEntity);
        return notebookEntity;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        this.notebookRepo.deleteById(UUID.fromString(id));
    }
}
