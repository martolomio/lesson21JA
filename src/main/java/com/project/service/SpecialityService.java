package com.project.service;


import com.project.dao.FacultyRepository;
import com.project.dao.SpecialityRepository;
import com.project.domain.Faculty;
import com.project.domain.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SpecialityService {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private RatingListService ratingListService;

    public List<Speciality> findAll() {
        return specialityRepository.findAll();
    }

    public List<Speciality> findByRecruitmentCompletedFalse() {
        return specialityRepository.findByRecruitmentCompletedFalse();
    }

    public boolean createSpeciality(Speciality speciality, Map<String, String> form) {
        Optional<Speciality> specialityFromDb = specialityRepository.findByTitle(speciality.getTitle());

        if (specialityFromDb.isPresent()) {
            return false;
        }

        Faculty faculty = parseFaculty(form);
        speciality.setFaculty(faculty);
        speciality.setRecruitmentCompleted(false);

        specialityRepository.save(speciality);
        return true;
    }

    public void updateSpeciality(Speciality speciality, Map<String, String> form) {
        Faculty faculty = parseFaculty(form);
        speciality.setFaculty(faculty);
        speciality.setRecruitmentCompleted(false);

        specialityRepository.save(speciality);
    }

    public void deleteSpeciality(Speciality speciality) {
        specialityRepository.delete(speciality);
    }

    public void completeRecruitment(Speciality speciality) {
        speciality.setRecruitmentCompleted(true);
        specialityRepository.save(speciality);

        ratingListService.completeRecruitmentBySpeciality(speciality);
    }

    public Faculty parseFaculty(Map<String, String> form) {
        Integer facultyId = Integer.valueOf(form.get("faculty"));
        Faculty faculty = facultyRepository.findById(facultyId).get();

        return faculty;
    }
}
