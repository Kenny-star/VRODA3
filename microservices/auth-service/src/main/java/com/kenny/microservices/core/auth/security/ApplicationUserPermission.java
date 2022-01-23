package com.kenny.microservices.core.auth.security;

public enum ApplicationUserPermission {
    CUSTOMER_READ("student:read"),
    CUSTOMER_WRITE("customer:write"),
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
