package com.example.freeforall.utils;

import com.example.freeforall.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtility {

    public static String currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;

        Object principal = auth.getPrincipal();

        if (principal instanceof UserEntity userEntity) {
            return userEntity.getEmail();
        }

        assert principal != null;
        return principal.toString();
    }
}
