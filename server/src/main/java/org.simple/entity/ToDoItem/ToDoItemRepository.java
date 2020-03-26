package org.simple.entity.ToDoItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {

    List<ToDoItem> findBytoDoGroupId(Long id);

    @Query(value = "SELECT text FROM org.simple.entity.ToDoItem.ToDoItem")
    List<String> getToDoTexts();
}