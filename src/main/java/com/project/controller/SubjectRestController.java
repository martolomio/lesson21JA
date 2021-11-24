package com.project.controller;

import com.project.domain.Speciality;
import com.project.dto.SubjectDTO;
import com.project.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class SubjectRestController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjectBySpeciality")
    public Set<SubjectDTO> viewSubjectsBySpeciality(@RequestParam("id") Speciality speciality) {
        return subjectService.findBySpeciality(speciality);
    }
}