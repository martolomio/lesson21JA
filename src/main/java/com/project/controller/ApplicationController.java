package com.project.controller;

import com.project.domain.Access;
import com.project.domain.Application;
import com.project.domain.RatingList;
import com.project.domain.User;
import com.project.service.ApplicationService;
import com.project.service.RatingListService;
import com.project.service.SpecialityService;
import com.project.service.SupportingDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private SpecialityService specialityService;
    @Autowired
    private SupportingDocumentService supportingDocumentService;
    @Autowired
    private RatingListService ratingListService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public String viewApplicationList(HttpServletRequest request, HttpSession session, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        List<Application> applicationsList = applicationService.findByApplicant(user.getApplicant());
        model.addAttribute("applications", applicationsList);
        model.addAttribute("applicationsStatus", applicationService.getApplicationsStatus(applicationsList));
        session.setAttribute("specialities", ratingListService.findSpecialitiesByApplicant(user.getId()));

        return "applicationList";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/create")
    public String viewCreationForm(Model model) {
        model.addAttribute("specialities", specialityService.findByRecruitmentCompletedFalse());

        return "applicationCreator";
    }

    @PreAuthorize("hasAuthority('USER')")
    @
            PostMapping("/create")
    public String createApplication(@RequestParam Map<String, String> form, @RequestParam("files") MultipartFile[] supportingDocuments,
                                    @Valid Application application, BindingResult bindingResult, Model model) throws IOException {
        Map<String, String> znoMarksErrors = applicationService.getZnoMarksErrors(form);
        Map<String, String> supportingDocumentErrors = supportingDocumentService.getSupportingDocumentErrors(supportingDocuments);

        if (bindingResult.hasErrors() || form.get("speciality") == "" || !znoMarksErrors.isEmpty() || !supportingDocumentErrors.isEmpty()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.mergeAttributes(supportingDocumentErrors);
            model.addAttribute(!znoMarksErrors.isEmpty() ? "message" : "", "При заповненні балів ЗНО були виявлені помилки: " +
                    znoMarksErrors.values() + ". Спробуйте заповнити форму ще раз!");
            model.addAttribute(form.get("speciality") == "" ? "specialityError" : "", "Поле Спеціальність не може бути порожнім!");
            model.addAttribute("specialities", specialityService.findByRecruitmentCompletedFalse());

            return "applicationCreator";
        }

        boolean applicationExists = !applicationService.createApplication(application, form, supportingDocuments);

        if (applicationExists) {
            model.addAttribute("message", "На цю спеціальніть форма вже створена!");
            model.addAttribute("specialities", specialityService.findByRecruitmentCompletedFalse());

            return "applicationCreator";
        }

        return "redirect:/application";
    }

    @GetMapping("/edit")
    public String viewEditForm(@RequestParam("id") Application application, Model model) {
        model.addAttribute("aplication", application);
        model.addAttribute("specialities", specialityService.findByRecruitmentCompletedFalse());
        model.addAttribute("downloadURI", ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").toUriString());

        return "applicationEditor";
    }

    @PostMapping("/edit")
    public String updateApplication(@RequestParam("id") Application application, @RequestParam Map<String, String> form,
                                    @RequestParam("files") MultipartFile[] supportingDocuments, HttpSession session, @Valid Application updatedApplication,
                                    BindingResult bindingResult, Model model) throws IOException {
        Map<String, String> znoMarksErrors = applicationService.getZnoMarksErrors(form);
        Map<String, String> supportingDocumentErrors = supportingDocumentService.getSupportingDocumentErrors(supportingDocuments);

        if (bindingResult.hasErrors() || !znoMarksErrors.isEmpty() || !supportingDocumentErrors.isEmpty()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.mergeAttributes(znoMarksErrors);
            model.mergeAttributes(supportingDocumentErrors);
            model.addAttribute("aplication", application);
            model.addAttribute("specialities", specialityService.findByRecruitmentCompletedFalse());
            model.addAttribute("downloadURI", ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").toUriString());

            return "applicationEditor";
        }

        applicationService.updateApplication(updatedApplication, form, supportingDocuments);

        if (((User) session.getAttribute("user")).getAccessLevels().contains(Access.valueOf("ADMIN"))) {
            return "redirect:/application/notAcceptedApps";
        }

        return "redirect:/application";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/delete")
    public String deleteApplication(@RequestParam("id") Application application) {
        applicationService.deleteApplication(application);

        return "redirect:/application";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/notAcceptedApps")
    public String viewNotAcceptedApps(HttpSession session) {
        List<RatingList> notAcceptedApps = ratingListService.findNotAcceptedApps();

        session.setAttribute("notAcceptedApps", notAcceptedApps);

        if (notAcceptedApps.isEmpty()) {
            return "redirect:/main";
        }

        return "notAcceptedApps";
    }	}
