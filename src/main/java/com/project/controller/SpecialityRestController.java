package com.project.controller;

import com.project.dto.SpecialityDTO;
import com.project.service.RatingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class SpecialityRestController {
    @Autowired
    private RatingListService ratingListService;

    @GetMapping("/specialitiesByApplicant")
    public Set<SpecialityDTO> viewSpecialitiesByApplicant(@RequestParam("id") Integer applicantId) {
        return ratingListService.parseSpecialitiesByApplicant(applicantId);
    }
}