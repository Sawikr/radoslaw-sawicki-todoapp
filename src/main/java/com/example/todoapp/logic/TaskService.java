package com.example.todoapp.logic;

import com.example.todoapp.mod.Task;
import com.example.todoapp.mod.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Asychroniczność, czyli osobny wątek do działania!
 * Klasa nie jest używana!
 */
@Service
public class TaskService {
    public static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    public final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    //zapewnienie asynchroniczności poprzez CompletableFuture
    //ogólna pula, może nie najlepsze rozwiązanie, ale działa
    @Async
    public CompletableFuture <List<Task>> findAllAsync (){
        logger.info("Supply async!");
        return CompletableFuture.supplyAsync(()-> repository.findAll());//lub 'repository::findAll()'
    }
}
