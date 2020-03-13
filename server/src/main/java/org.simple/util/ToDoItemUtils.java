package org.simple.util;

import org.simple.entity.ToDoItem;
import org.simple.entity.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToDoItemUtils {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    public List<ToDoItem> getToDoList(){
        return this.toDoItemRepository.findAll();
    }

    public List<ToDoItem> getToDoByGroupId(Integer group_id){
        return this.toDoItemRepository.findBytoDoGroupId(group_id);
    }
}