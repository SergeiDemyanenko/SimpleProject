package org.simple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String USER_PARAM = "user";

    @Value("${application.authorization.enabled}")
    private Boolean authorizationEnabled;

    @Value("${application.authorization.redirect:#{null}}")
    private String authorizationRedirect;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (Boolean.TRUE.equals(authorizationEnabled)
                && request.getSession().getAttribute(USER_PARAM) == null
                && !request.getRequestURI().equals("/login") && !request.getRequestURI().equals("/api/login"))
        {
            if (authorizationRedirect == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            } else {
                response.sendRedirect(authorizationRedirect);
            }
        }

        return true;
    }
}
