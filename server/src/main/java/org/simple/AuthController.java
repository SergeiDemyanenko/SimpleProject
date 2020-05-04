package org.simple;

import org.simple.Helpers.JwtUtil;
import org.simple.Helpers.SimpleResponse;
import org.simple.entity.User.UserAut;
import org.simple.entity.User.UserEntity;
import org.simple.entity.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Controller.API_PREFIX)
public class AuthController {

    public static final String LOGIN = "/login";
    public static final String SIGNUP = "/signUp";

    public static String token;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public SimpleResponse login(@RequestBody UserAut userAut) {
        if (userRepository.findByLogin(userAut.getLogin()).size() != 0) {
            if (passwordEncoder.matches(
                    userAut.getPassword(), userRepository.findByLogin(userAut.getLogin()).get(0).getPassword()))
            {
                token = jwtUtil.generateToken(userAut);
                return new SimpleResponse("Login is success", true, token);
            } else {
                return new SimpleResponse("Password is incorrect", false);
            }
        } else {
            return new SimpleResponse("User is not exist", false);
        }
    }

    @RequestMapping(value = SIGNUP, method = RequestMethod.POST)
    public SimpleResponse signUp(@RequestBody UserAut userAut) {
        if (userRepository.findByLogin(userAut.getLogin()).size() == 0) {
            String encodedPassword = passwordEncoder.encode(userAut.getPassword());
            userRepository.save(new UserEntity(userAut.getLogin(), encodedPassword));
            return new SimpleResponse("Registration is success", true);
        } else {
            return new SimpleResponse("User already exist", false);
        }
    }
}
