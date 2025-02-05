package com.iposhka.filter;

import com.iposhka.dto.SessionDto;
import com.iposhka.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import java.util.Optional;
import java.util.UUID;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final SessionService sessionService;

    public AuthInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie cookie = WebUtils.getCookie(request, "sessionId");
        if(cookie == null){
            response.sendRedirect("/auth/sign-in");
            return false;
        }

        Optional<SessionDto> maybeSession;
        try{
            maybeSession = checkSession(cookie);
        }catch (IllegalArgumentException e){
            maybeSession = Optional.empty();
        }

        if(maybeSession.isEmpty()){
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            response.sendRedirect("/auth/sign-in");
            return false;
        }

        return true;
    }

    private Optional<SessionDto> checkSession(Cookie cookie){
        UUID id = UUID.fromString(cookie.getValue());
        if (id == null){
            return Optional.empty();
        }

        return sessionService.findByUUID(id);
    }
}
