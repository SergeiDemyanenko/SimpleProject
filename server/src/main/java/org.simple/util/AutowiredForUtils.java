package org.simple.util;

import org.simple.entity.ToDoGroupRepository;
import org.simple.entity.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutowiredForUtils {

    private static ToDoItemRepository toDoItemRepository;

    @Autowired
    public void setToDoItemRepository(ToDoItemRepository been){
        toDoItemRepository = been;
    }

    public static ToDoItemRepository getToDoItemRepository(){
        return toDoItemRepository;
    }

    private static ToDoGroupRepository toDoGroupRepository;

    @Autowired
    public void setToDoGroupRepository(ToDoGroupRepository been){
        toDoGroupRepository = been;
    }

    public static ToDoGroupRepository getToDoGroupRepository(){
        return toDoGroupRepository;
    }
}
