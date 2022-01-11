package com.admissionsOffice.controller;

import com.admissionsOffice.domain.Speciality;
import com.admissionsOffice.dto.SubjectDTO;
import com.admissionsOffice.service.SubjectService;
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