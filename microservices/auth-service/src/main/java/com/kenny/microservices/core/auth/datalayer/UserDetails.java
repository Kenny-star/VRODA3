package com.kenny.microservices.core.auth.datalayer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}