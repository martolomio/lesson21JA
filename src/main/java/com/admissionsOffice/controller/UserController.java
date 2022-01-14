package com.admissionsOffice.controller;

import com.admissionsOffice.domain.Access;
import com.admissionsOffice.domain.User;
import com.admissionsOffice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("accessLevels", Access.values());

        return "userEditor";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam Map<String, String> form, @RequestParam("userId") User user, Model model) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isEmpty(form.get("firstName"))) {
            errors.put("firstNameError", "Це поле не може бути порожнім!");
        }

        if (StringUtils.isEmpty(form.get("lastName"))) {
            errors.put("lastNameError", "Це поле не може бути порожнім!");
        }

        if (!errors.isEmpty()) {
            model.mergeAttributes(errors);
            model.addAttribute("user", user);
            model.addAttribute("accessLevels", Access.values());

            return "userEditor";
        }

        userService.saveUser(user, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", userService.findById(user.getId()));

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam(required = false) String birthDate,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String school,
            @RequestParam(required = false) MultipartFile photo,
            @RequestParam(required = false) String removePhotoFlag,
            Model model) throws IOException {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isEmpty(firstName)) {
            errors.put("firstNameError", "Це поле не може бути порожнім!");
        }

        if (StringUtils.isEmpty(lastName)) {
            errors.put("lastNameError", "Це поле не може бути порожнім!");
        }

        if (StringUtils.isEmpty(email)) {
            errors.put("emailError", "Це поле не може бути порожнім!");
        }

        if (password.length() < 6) {
            errors.put("passwordError", "Пароль має бути не коротший за 6 символів");
        }

        if (confirmPassword.length() < 6) {
            errors.put("confirmPasswordError", "Пароль має бути не коротший за 6 символів");
        }

        if (password != "" && confirmPassword != "" && !password.equals(confirmPassword)) {
            errors.put("confirmPasswordError", "Паролі не співпадають!");
        }

        if (user.getAccessLevels().contains(Access.valueOf("USER"))) {
            if (!photo.isEmpty() && !photo.getContentType().contains("image")) {
                errors.put("photoError", "Файл має бути малюнком");
            }
        }

        if (!errors.isEmpty()) {
            model.mergeAttributes(errors);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("email", email);
            model.addAttribute("birthDate", birthDate);
            model.addAttribute("city", city);
            model.addAttribute("school", school);

            return "profile";
        }

        userService.updateProfile(user, firstName, lastName, email, password, birthDate, city, school, photo, removePhotoFlag);

        return "redirect:/home";
    }
}
