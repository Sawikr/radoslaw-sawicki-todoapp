package com.example.todoapp.adapter;

import com.example.todoapp.mod.TaskGroup;
import com.example.todoapp.mod.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SQLTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {

    @Override
    //zapytanie na encjach - HQL - bliżej do Javy niż do SQL
    @Query("select distinct g from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();
}
