package org.simple.entity.ToDoItem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.simple.entity.ToDoGroup.ToDoGroup;
import org.simple.entity.User.User;

import javax.persistence.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "todo_list")
public class ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private ToDoGroup toDoGroup;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ToDoItem() {}

    public ToDoItem(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public ToDoItem(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}