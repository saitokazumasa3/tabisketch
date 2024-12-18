package com.tabisketch.controller;

import com.tabisketch.service.IEditPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/edit/password")
public class EditPasswordController {

    private final IEditPasswordService editPasswordService;

    @Autowired
    public EditPasswordController(IEditPasswordService editPasswordService) {
        this.editPasswordService = editPasswordService;
    }

    @GetMapping
    public String showPasswordEditForm() {
        return "user/edit/password";
    }

    @PostMapping
    public String editPassword(@RequestParam String currentPassword,
                               @RequestParam String password,
                               @RequestParam String rePassword) {
        if (!password.equals(rePassword)) {
            return "user/edit/password";
        }

        boolean isPasswordUpdated = editPasswordService.editPassword("sample@example.com", password);
        if (isPasswordUpdated) {
            return "redirect:/user/login";
        } else {
            return "user/edit/password";
        }
    }
}
