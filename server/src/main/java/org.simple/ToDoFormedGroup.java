package org.simple;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ToDoFormedGroup {

    private int group_id;
    private String group_name;
    private List<ToDoItem> todoFormedGroup;

    public ToDoFormedGroup(int group_id, String group_name, List<ToDoItem> toDoFormedGroup) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.todoFormedGroup = toDoFormedGroup;
    }

    public int getGroup_id() {
        return group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public List<ToDoItem> getTodoFormedGroup() {
        return todoFormedGroup;
    }
}