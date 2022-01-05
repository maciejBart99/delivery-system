package com.lukasikm.delivery.authserviceclient.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CredentialsDTO {
    @NotNull
    private final String email;
    @NotNull
    private final String password;
}
