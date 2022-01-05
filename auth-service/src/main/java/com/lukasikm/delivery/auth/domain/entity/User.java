package com.lukasikm.delivery.auth.domain.entity;

import com.lukasikm.delivery.authserviceclient.dto.UserDTO;
import com.lukasikm.delivery.authserviceclient.dto.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Data
@AllArgsConstructor
public class User {
    @PrimaryKey
    private UUID id;
    @Indexed
    private String email;
    private String passwordHash;
    private UserRole role;

    public UserDTO toDto() {
        return new UserDTO(id.toString(), email, role);
    }
}
