package com.kenny.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private String userId = null;

    private String firstName = null;

    private String lastName = null;

    private String userName = null;

    private String password = null;

    private String roles = null;
}