package com.example.todoapp.controller;

import com.example.todoapp.mod.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    /**
     * Używamy tu Model to kontaktowania się formularzem projects.html
     */
    @GetMapping
    String showProjects(Model model){
        var projectToEdit = new ProjectWriteModel();
        projectToEdit.setDescription("test");
        model.addAttribute("project", projectToEdit);
        return "projects";
    }
}