package org.simple.util;

import org.simple.entity.ToDoGroup;
import org.simple.entity.ToDoGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToDoGroupUtils {

    @Autowired
    ToDoGroupRepository toDoGroupRepository;

    public List<ToDoGroup> getToDoGroup(){
        return this.toDoGroupRepository.findAll();
    }
}