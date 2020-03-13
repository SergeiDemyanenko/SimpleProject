package org.simple.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoGroupRepository extends JpaRepository<ToDoGroup, Long> {

    ToDoGroup findById(Integer group_id);
}