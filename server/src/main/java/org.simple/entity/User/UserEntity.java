package org.simple.entity.User;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "users")
public class UserEntity extends UserAut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserEntity(String login, String password) {
        super(login, password);
    }

    public UserEntity(){
        super();
    }

    public Long getId() {
        return id;
    }
}

