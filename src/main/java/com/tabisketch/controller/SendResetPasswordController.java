package com.tabisketch.controller;

import com.tabisketch.bean.form.SendResetPasswordForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.service.implement.SendResetPasswordService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/password-reset")
public class SendResetPasswordController {
    private final SendResetPasswordService resetPasswordSendService;

    public SendResetPasswordController(final SendResetPasswordService resetPasswordSendService) {
        this.resetPasswordSendService = resetPasswordSendService;
    }

    @GetMapping
    public String get(final Model model) {
        final var sendResetPasswordForm = SendResetPasswordForm.empty();
        model.addAttribute("sendResetPasswordForm", sendResetPasswordForm);
        return "password-reset/index";
    }

    @PostMapping
    public String post(
            final @Validated SendResetPasswordForm sendResetPasswordForm,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes
    ) throws MessagingException, InsertFailedException, SelectFailedException {
        if (bindingResult.hasErrors()) return "password-reset/index";

        this.resetPasswordSendService.execute(sendResetPasswordForm);

        redirectAttributes.addFlashAttribute("mailAddress", sendResetPasswordForm.getMailAddress());
        return "redirect:password-reset/send";
    }

    @GetMapping("/send")
    public String send(final Model model) {
        if (!model.containsAttribute("mailAddress")) return "password-reset/index";

        return "password-reset/send";
    }
}
