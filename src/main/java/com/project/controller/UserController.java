package com.project.controller;

import com.project.dao.UserRepository;
import com.project.domain.Access;
import com.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("{user}")
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    public String userSave(@RequestParam Map<String, String> form, @RequestParam("userId") User user){
       user.setFirstName(form.get("firstName"));
       user.setLastName(form.get("lastName"));
       user.setEmail(form.get("email"));

       if(form.keySet().contains("active")){
           user.setActive(true);
       }else {
           user.setActive(false);
       }
       user.getAccess().clear();
        Set<String> access = Arrays.stream(Access.values()).map(Access::name).collect(Collectors.toSet());
        for(String key: form.keySet()){

            if (access.contains(key)){
                user.getAccess().add(Access.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:/user";
    }
}
