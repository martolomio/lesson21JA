package com.project.controller;

import com.project.domain.Faculty;
import com.project.service.FacultyService;
import com.project.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/faculty")
@PreAuthorize("hasAuthority('ADMIN')")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String viewFacultyList(Model model) {
        List<Faculty> facultiesList = facultyService.findAll();
        model.addAttribute("faculties", facultiesList);

        return "facultyList";
    }

    @GetMapping("/create")
    public String viewCreationForm(Model model) {
        model.addAttribute("subjects", subjectService.findAll());

        return "facultyCreator";
    }

    @PostMapping("/create")
    public String createFaculty(@RequestParam Map<String, String> form, @Valid Faculty faculty, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("subjects", subjectService.findAll());

            return "facultyCreator";
        }

        boolean facultyExists = !facultyService.createFaculty(faculty, form);

        if (facultyExists) {
            model.addAttribute("message", "Такий факультет вже є!");
            model.addAttribute("subjects", subjectService.findAll());

            return "facultyCreator";
        }

        return "redirect:/faculty/";
    }

    @GetMapping("/edit")
    public String viewEditForm(@RequestParam("id") Faculty faculty, Model model) {
        model.addAttribute("faculty", faculty);
        model.addAttribute("subjects", subjectService.findAll());

        return "facultyEditor";
    }

    @PostMapping("/edit")
    public String updateFaculty(@RequestParam("id") Faculty faculty, @RequestParam Map<String, String> form, @Valid Faculty updatedFaculty, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("faculty", faculty);
            model.addAttribute("subjects", subjectService.findAll());

            return "facultyEditor";
        }

        facultyService.updateFaculty(updatedFaculty, form);

        return "redirect:/faculty";
    }

    @GetMapping("/delete")
    public String deleteFaculty(@RequestParam("id") Faculty faculty) {
        facultyService.deleteFaculty(faculty);

        return "redirect:/faculty";
    }
}
