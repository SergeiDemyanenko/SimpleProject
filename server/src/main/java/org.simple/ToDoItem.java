package org.simple;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ToDoItem {

    private int id;
    private String text;
    private int group_id;

    public ToDoItem(int id, String text, int group_id) {
        this.id = id;
        this.text = text;
        this.group_id = group_id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}