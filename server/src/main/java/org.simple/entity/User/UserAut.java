package org.simple.entity.User;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class UserAut {

    private String login;

    private String password;

    public UserAut() {
    }

    public UserAut(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
