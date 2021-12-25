package com.project.controller;

import com.project.domain.Access;
import com.project.domain.Applicant;
import com.project.domain.Speciality;
import com.project.domain.User;
import com.project.service.ApplicationService;
import com.project.service.RatingListService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/home")
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private RatingListService ratingListService;

    @GetMapping
    public String viewMainPage(HttpSession session, Model model) throws UnsupportedEncodingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        User userFromDb = userService.findById(user.getId());

        session.setAttribute("user", userFromDb);

        if (userFromDb.getAccessLevels().contains(Access.valueOf("USER"))) {
            List<Speciality> specialitiesByApplicant = ratingListService.findSpecialitiesAppliedByApplicant(userFromDb.getId());
            Map<Speciality, Set<Applicant>> enrolledApplicants = new HashMap<>();

            for (Speciality speciality : specialitiesByApplicant) {
                enrolledApplicants.put(speciality, ratingListService.getEnrolledApplicantsBySpeciality(speciality));
            }

            session.setAttribute("photo", userService.parseFileData(userFromDb));
            session.setAttribute("specialities", specialitiesByApplicant);
            model.addAttribute("submittedApps", ratingListService.parseNumberOfApplicationsBySpeciality());
            model.addAttribute("isRejectedAppsPresent", applicationService.checkForRejectedApplications(applicationService.findByApplicant(userFromDb.getApplicant())));
            model.addAttribute("enrolledApplicants", enrolledApplicants);
        }

        if (userFromDb.getAccessLevels().contains(Access.valueOf("ADMIN"))) {
            session.setAttribute("notAcceptedApps", ratingListService.findNotAcceptedApps());
        }

        return "main";
    }
}