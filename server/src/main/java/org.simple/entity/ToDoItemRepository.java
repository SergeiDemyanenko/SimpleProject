package org.simple.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {

    ToDoItem findById(Integer id);

    List<ToDoItem> findBytoDoGroupId(Integer id);

    @Transactional
    void deleteById(Integer id);

    @Query(value = "SELECT text FROM ToDoItem")
    List<String> getToDoTexts();
}