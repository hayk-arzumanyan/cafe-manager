package com.task.cafemanager.api.facade.utils;

import com.task.cafemanager.api.facade.security.model.MyUserDetails;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthenticationUtils {
    private AuthenticationUtils() {
        throw new IllegalAccessError("Utility class cannot be instantiated.");
    }

    public static Long getUserId() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetails) {
            return ((MyUserDetails) principal).getId();
        } else {
            throw new AuthenticationServiceException("Could not get the authenticated principal.");
        }
    }
}
