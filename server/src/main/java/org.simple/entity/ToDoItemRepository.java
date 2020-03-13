package org.simple.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {

    ToDoItem findById(Integer id);

    List<ToDoItem> findBytoDoGroupId(Integer id);
}