package com.project.service;


import com.project.dao.FacultyRepository;
import com.project.dao.SubjectRepository;
import com.project.domain.Faculty;
import com.project.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    public boolean createFaculty(Faculty faculty, Map<String, String> form) {
        Optional<Faculty> facultyFromDb = facultyRepository.findByTitle(faculty.getTitle());

        if (facultyFromDb.isPresent()) {
            return false;
        }

        facultyRepository.save(faculty);
        updateFaculty(faculty, form);
        return true;
    }

    public void updateFaculty(Faculty faculty, Map<String, String> form) {
        Set<Subject> examSubjects = parseExamSubjects(form);
        faculty.setExamSubjects(examSubjects);

        facultyRepository.save(faculty);
    }

    public void deleteFaculty(Faculty faculty) {
        facultyRepository.delete(faculty);
    }

    public Set<Subject> parseExamSubjects(Map<String, String> form) {
        Set<String> subjectTitles = subjectRepository.findAll().stream().map(Subject::getTitle).collect(Collectors.toSet());
        Set<Subject> examSubjects = new HashSet<>();

        for (String key : form.keySet()) {
            if (subjectTitles.contains(form.get(key))) {
                examSubjects.add(new Subject(Integer.valueOf(key), form.get(key)));
            }
        }
        return examSubjects;
    }
}
