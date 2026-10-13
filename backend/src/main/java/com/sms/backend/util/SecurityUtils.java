package com.sms.backend.util;

import com.sms.backend.exception.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static String getCurrentUserEmail() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {

            throw new BadRequestException("User not authenticated");
        }

        return authentication.getName();
    }

    public static boolean hasRole(String role) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return false;
        }

        String roleAuthority = "ROLE_" + role;

        return authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals(roleAuthority));
    }
}
