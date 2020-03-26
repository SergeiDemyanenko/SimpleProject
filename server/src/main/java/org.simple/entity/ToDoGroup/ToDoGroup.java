package org.simple.entity.ToDoGroup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.simple.entity.ToDoItem.ToDoItem;

import javax.persistence.*;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "todo_group")
public class ToDoGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany
    @JoinColumn(name = "group_id")
    @OrderBy("id")
    private List<ToDoItem> toDoItems;

    public ToDoGroup() {}

    public ToDoGroup(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<ToDoItem> getToDoItems() {
        return toDoItems;
    }
}