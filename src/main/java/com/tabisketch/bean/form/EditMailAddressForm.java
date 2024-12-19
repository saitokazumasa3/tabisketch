package com.tabisketch.bean.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditMailAddressForm {
    @Email
    @NotBlank
    private String currentMailAddress;

    @Email
    @NotBlank
    private String newMailAddress;

    @NotBlank
    private String currentPassword;

    public static EditMailAddressForm empty() {
        return new EditMailAddressForm("", "", "");
    }
}
