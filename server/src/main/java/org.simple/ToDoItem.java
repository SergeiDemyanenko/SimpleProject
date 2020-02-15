package org.simple;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ToDoItem {

    private int id;
    private String text;

    public ToDoItem(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getID() {
        return id;
    }

    public String getText() {
        return text;
    }
}