package com.admissionsOffice.controller;

import com.admissionsOffice.domain.User;
import com.admissionsOffice.dto.CaptchaResponse;
import com.admissionsOffice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/registration")
    public String viewRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @RequestParam("g-recaptcha-response") String reCaptchaResponse,
            @RequestParam String confirmPassword,
            @Valid User user,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redir) {
        String url = String.format(CAPTCHA_URL, secret, reCaptchaResponse);
        CaptchaResponse captchaResponse = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);

        if (StringUtils.isEmpty(confirmPassword) || bindingResult.hasErrors() || !captchaResponse.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("confirmPasswordError", "Пароль має бути не коротший 6 символів");
            model.addAttribute("captchaError", "заповніть капчу!");
            return "registration";
        }

        if (user.getPassword() != null && !user.getPassword().equals(confirmPassword)) {
            model.addAttribute("confirmPasswordError2", "Введені паролі не співпадають");
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("userExistsMessage", "Такий користувач вже є!");
            return "registration";
        }

        redir.addFlashAttribute("activationMessage", "Для активації перейдіть за посиланням в листі висланому на Вашу електронну пошту!");
        return "redirect:/login/";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("activationSucceedMessage", "Користувача активовано!");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("activationFailedMessage", "Код активації не знайдений!");
        }

        return "login";
    }
}