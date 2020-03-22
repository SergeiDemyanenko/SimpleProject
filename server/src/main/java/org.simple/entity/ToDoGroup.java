package org.simple.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "todo_group")
public class ToDoGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_name")
    private String group_name;

    @OneToMany
    @JoinColumn(name = "group_id")
    @OrderBy("id")
    private List<ToDoItem> toDoItems;

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