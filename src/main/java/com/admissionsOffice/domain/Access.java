package com.admissionsOffice.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Access implements GrantedAuthority {
    ADMINISTRATOR, USER;
    @Override
    public String getAuthority(){
        return name();
    }
}
