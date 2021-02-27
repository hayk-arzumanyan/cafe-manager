package com.task.cafemanager.api.facade.utils;

import com.task.cafemanager.api.facade.security.model.MyUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthenticationUtils {

    public static Long getUserId() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetails) {
            return ((MyUserDetails) principal).getId();
        } else {
            throw new AuthenticationServiceException("Could not get the authenticated principal.");
        }
    }
}
