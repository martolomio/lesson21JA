package com.admissionsOffice.controller;

import com.admissionsOffice.domain.Subject;
import com.admissionsOffice.service.SubjectService;
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
@RequestMapping("/subject")
@PreAuthorize("hasAuthority('ADMIN')")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String viewSubjectList(Model model) {
        List<Subject> subjectsList = subjectService.findAll();
        model.addAttribute("subjects", subjectsList);

        return "subjectList";
    }

    @GetMapping("/create")
    public String viewCreationForm() {
        return "subjectCreator";
    }

    @PostMapping("/create")
    public String createSubject(@Valid Subject subject,	BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "subjectCreator";
        }

        boolean subjectExists = !subjectService.createSubject(subject);

        if (subjectExists) {
            model.addAttribute("subjectExistsMessage", "Цей предмет вже є!");
            return "subjectCreator";
        }

        return "redirect:/subject/";
    }

    @GetMapping("/edit")
    public String viewEditForm(@RequestParam("id") Subject subject, Model model) {
        model.addAttribute("subject", subject);

        return "subjectEditor";
    }

    @PostMapping("/edit")
    public String saveSubject(@RequestParam("id") Subject subject, @Valid Subject updatedSubject, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("subject", subject);

            return "subjectEditor";
        }

        subjectService.saveSubject(updatedSubject);

        return "redirect:/subject";
    }

    @GetMapping("/delete")
    public String deleteSubject(@RequestParam("id") Subject subject) {
        subjectService.deleteSubject(subject);

        return "redirect:/subject";
    }
}