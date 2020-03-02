package org.simple;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ToDoGroup {

    private int group_id;
    private String group_name;

    public ToDoGroup(int group_id, String group_name) {
        this.group_id = group_id;
        this.group_name = group_name;
    }

    public ToDoGroup(int group_id) {
        this.group_id = group_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public String getGroup_name() {
        return group_name;
    }
}