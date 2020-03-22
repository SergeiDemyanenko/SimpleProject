package org.simple.entity.ToDoGroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoGroupRepository extends JpaRepository<ToDoGroup, Long> {

    ToDoGroup findById(Integer group_id);

    List<ToDoGroup> findAllByOrderById();
}