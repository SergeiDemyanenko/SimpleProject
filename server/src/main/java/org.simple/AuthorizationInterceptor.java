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

    @Value("${application.authorization.redirect}")
    private String authorizationRedirect;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (Boolean.TRUE.equals(authorizationEnabled)
                && request.getSession().getAttribute(USER_PARAM) == null
                && !request.getRequestURI().equals(authorizationRedirect)
                && !request.getRequestURI().equals(Controller.API_PREFIX + Controller.LOGIN))
        {
            if (request.getRequestURI().startsWith(Controller.API_PREFIX)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            } else {
                response.sendRedirect(authorizationRedirect);
            }
        }

        return true;
    }
}
