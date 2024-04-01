/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.todoapp.adapter;

import com.example.todoapp.mod.Task;
import com.example.todoapp.mod.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Admin
 */

@Repository
public interface SQLTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    /**
     * Tworzymy natywny SQL, czyli czysty typowy SQL, rozwijamy nasze zapytanie
     * @param Id
     * @return boolen
     */
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer Id);

    //metoda - w niezamkniętych zadaniach szukamy w grupie ID
    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupID);
}