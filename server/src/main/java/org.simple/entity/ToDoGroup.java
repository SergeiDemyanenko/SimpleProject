package org.simple.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "TODO_GROUP")
public class ToDoGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "GROUP_NAME")
    private String group_name;

    public ToDoGroup() {
    }

    public Integer getId() {
        return id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public ToDoGroup(Integer group_id, String group_name) {
        this.id = group_id;
        this.group_name = group_name;
    }
}