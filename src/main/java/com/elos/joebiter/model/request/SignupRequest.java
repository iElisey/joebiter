package com.elos.joebiter.model.request;

import com.elos.joebiter.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private Set<Role> roles;
}
