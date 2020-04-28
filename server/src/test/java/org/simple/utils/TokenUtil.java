package org.simple.utils;

import org.springframework.stereotype.Service;

@Service
public class TokenUtil {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
