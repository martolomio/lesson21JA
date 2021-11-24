package com.project.controller;

import com.project.domain.Applicant;
import com.project.domain.Speciality;
import com.project.service.RatingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/ratingList")
public class RatingListController {
    @Autowired
    private RatingListService ratingListService;

    @GetMapping("/speciality")
    public String viewApplicantsRankBySpeciality(@RequestParam("id") Speciality speciality, HttpServletRequest request, Model model) throws URISyntaxException {
        Map<Applicant, Double> applicantsRank = ratingListService.parseApplicantsRankBySpeciality(speciality.getId());
        Set<Applicant> enrolledApplicants = ratingListService.getEnrolledApplicantsBySpeciality(speciality);

        model.addAttribute("speciality", speciality);
        model.addAttribute("applicantsRank", applicantsRank);
        model.addAttribute("enrolledApplicants", enrolledApplicants);
        model.addAttribute("refererURI", new URI(request.getHeader("referer")).getPath());

        return "ratingList";
    }
}
