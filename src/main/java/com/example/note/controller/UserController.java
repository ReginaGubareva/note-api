package com.example.note.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHello(){
        return "Hello user!";
    }
}
