package org.simple.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ToDoGroupRepository extends JpaRepository<ToDoGroup, Long> {

    ToDoGroup findById(Integer group_id);

    List<ToDoGroup> findAllByOrderById();

    @Transactional
    void deleteById(Integer id);
}