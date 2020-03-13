package org.simple.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "todo_list")
public class ToDoItem implements Comparable<ToDoItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TEXT")
    private String text;

    public ToDoItem() {
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