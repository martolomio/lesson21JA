package com.project.service;

import com.project.dao.SubjectRepository;
import com.project.domain.Speciality;
import com.project.domain.Subject;
import com.project.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Set<SubjectDTO> findBySpeciality(Speciality speciality) {
        Set<Subject> subjects = speciality.getFaculty().getExamSubjects();

        return subjects.stream().map(subject -> new SubjectDTO(subject.getId(), subject.getTitle()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public boolean createSubject(Subject subject) {
        Optional<Subject> subjectFromDb = subjectRepository.findByTitle(subject.getTitle());

        if (subjectFromDb.isPresent()) {
            return false;
        }

        subjectRepository.save(subject);
        return true;
    }

    public void saveSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    public void deleteSubject(Subject subject) {
        subjectRepository.delete(subject);
    }
}