package org.simple.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "todo_list")
public class ToDoItem implements Comparable<ToDoItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private ToDoGroup toDoGroup;

    public ToDoItem() {
    }

    public ToDoItem(String text, ToDoGroup toDoGroup) {
        this.text = text;
        this.toDoGroup = toDoGroup;
    }

    public ToDoItem(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(ToDoItem toDoItem) {
        return 0;
    }
}