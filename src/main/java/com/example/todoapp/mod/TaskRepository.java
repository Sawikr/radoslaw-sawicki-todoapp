package com.example.todoapp.mod;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();

    List<Task> findByDone(@Param("state") boolean done);

    Optional<Task> findById(Integer id);

    boolean existsById(Integer Id);

    boolean existsByDoneIsFalseAndGroup_Id(Integer groupID);

    Task save(Task entity);

    Page<Task> findAll(Pageable page);

    Task deleteById(int id);



}
