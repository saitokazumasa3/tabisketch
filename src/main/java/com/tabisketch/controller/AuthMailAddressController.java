package com.tabisketch.controller;

import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.service.IAuthMailAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mail/confirm/{token}")
public class AuthMailAddressController {
    private final IAuthMailAddressService authMailAddressService;

    public AuthMailAddressController(final IAuthMailAddressService authMailAddressService) {
        this.authMailAddressService = authMailAddressService;
    }

    @GetMapping
    public String get(final @PathVariable String token) throws DeleteFailedException, UpdateFailedException {
        this.authMailAddressService.execute(token);
        return "mail/confirm";
    }
}
