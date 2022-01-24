package com.kenny.microservices.core.auth.security.datalayer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.*;

import static javax.persistence.FetchType.*;
@Entity
@Data
@Table(name = "user")
@Builder(builderMethodName = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Type(type = "uuid-char")
    @Column(name = "user_id",unique = true, nullable = false)
    @Builder.Default
    private UUID userId = UUID.randomUUID();

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = EAGER)
    @Column(name = "roles")
    private Collection<Role> roles = new ArrayList();
}
