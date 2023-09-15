package com.fanplayground.fanplayground.jwt;

import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {
    public static Optional<User> getPrincipal() {
        // of() 인자로서 null 값을 받지 않는다.
        // ofNullable() null 값을 허용한다.
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(UserDetailsImpl.class::cast)
                .map(UserDetailsImpl::getUser); // 형태를 바꾼다. 생성자가 존재할것이다.
    }
}
