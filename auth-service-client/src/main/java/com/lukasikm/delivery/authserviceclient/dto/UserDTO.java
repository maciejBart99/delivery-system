package com.lukasikm.delivery.authserviceclient.dto;

import lombok.Data;

@Data
public class UserDTO {
    private final String id;
    private final String email;
    private final UserRole role;
}
