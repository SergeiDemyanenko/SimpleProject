package org.simple;

import org.springframework.data.repository.CrudRepository;

public interface TaskDB extends CrudRepository<Task, Integer> {

}
