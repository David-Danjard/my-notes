package com.my.notes.ui.controllers;

import com.my.notes.ui.repository.NotesRepo;
import com.my.notes.ui.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyNotesController {

    private final NotesRepo notesRepo;
    private final UserRepo userRepo;

    public MyNotesController(NotesRepo notesRepo, UserRepo userRepo) {
        this.notesRepo = notesRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("notes", notesRepo.getNotes());
        model.addAttribute("users", userRepo.getUsers());
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/accueil";
    }

}
