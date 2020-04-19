package com.my.notes.ui.controllers;

import com.my.notes.ui.services.business.NotesServices;
import com.my.notes.ui.services.business.UsersServices;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyNotesController {

    private final NotesServices notesServices;
    private final UsersServices usersServices;

    public MyNotesController(NotesServices notesRepo, UsersServices usersServices) {
        this.notesServices = notesRepo;
        this.usersServices = usersServices;
    }

    @GetMapping("/")
    public String indexPage(Model model, Authentication authentication) {
        model.addAttribute("notes", notesServices.getNotesByUser(authentication.getName()).getNotes());
        model.addAttribute("users", usersServices.getUsers().getUserBean());
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }

}
