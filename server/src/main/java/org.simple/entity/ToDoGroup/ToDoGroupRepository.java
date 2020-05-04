package org.simple.entity.ToDoGroup;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ToDoGroupRepository extends JpaRepository<ToDoGroup, Long> {

    ToDoGroup findById(long id);

    List<ToDoGroup> findAllByOrderById();

    @Transactional
    void deleteById(Long id);
}