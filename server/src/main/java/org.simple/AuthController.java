package org.simple;

import org.simple.Helpers.SimpleResponse;
import org.simple.entity.User.User;
import org.simple.entity.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(org.simple.Controller.API_PREFIX)
public class AuthController {

    public static final String LOGIN = "/login";
    public static final String SIGNUP = "/signUp";

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public SimpleResponse login(@RequestBody User user, HttpServletRequest requestServlet, HttpServletResponse responseServlet) {
        if (userRepository.findByLogin(user.getLogin()).size() != 0) {
            if (passwordEncoder.matches(
                    user.getPassword(), userRepository.findByLogin(user.getLogin()).get(0).getPassword()))
            {
                requestServlet
                        .getSession()
                        .setAttribute(AuthorizationInterceptor.USER_PARAM, user.getLogin());
                responseServlet
                        .setStatus(HttpServletResponse.SC_OK);
                return new SimpleResponse("Login is success", true);
            } else {
                return new SimpleResponse("Password is incorrect", false);
            }
        } else {
            return new SimpleResponse("User is not exist", false);
        }
    }

    @RequestMapping(value = SIGNUP, method = RequestMethod.POST)
    public SimpleResponse signUp(@RequestBody User user) {
        if (userRepository.findByLogin(user.getLogin()).size() == 0) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            userRepository.save(new User(user.getLogin(), encodedPassword));
            return new SimpleResponse("Registration is success", true);
        } else {
            return new SimpleResponse("User already exist", false);
        }
    }
}
