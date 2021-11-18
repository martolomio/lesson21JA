package com.project.controller;

import com.project.dao.UserRepository;
import com.project.domain.Access;
import com.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String viewRegistrationForm(){
        return "registration";
    }

    @PostMapping
    @ResponseBody
    public  String addUser(User user, Model model){
        User userFromDb = userRepository.findByEmail(user.getEmail());

        if(userFromDb != null){
            model.addAttribute("message", "User all ready exist  ");
            return "registration";
        }
        user.setAccess(Collections.singleton(Access.USER));
        userRepository.save(user);

        return  "redirect:/login";
    }

}
