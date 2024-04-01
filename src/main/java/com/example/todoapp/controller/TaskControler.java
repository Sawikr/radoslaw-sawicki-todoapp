/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.todoapp.controller;

import com.example.todoapp.logic.TaskService;
import com.example.todoapp.mod.Task;
import com.example.todoapp.mod.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * @author Admin
 */
//@RepositoryRestController
@RestController
//@RequestMapping("/tasks") - wtedy nie musimy wpisywać w ścieżce "/tasks" - nie używam tutaj!
public class TaskControler {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskControler.class);
    private final TaskRepository repository;
    private final TaskService service;

    //public TaskControler(@Qualifier("sqlTaskRepository")TaskRepository repository)... //to nie jest najlepsze rozwiązanie
    public TaskControler(TaskRepository repository, TaskService service) {
        this.repository = repository;
        this.service = service;
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {
        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    /**
     * Odpalimy ścieżkę tasks, jeżeli nie będzie atrybutów {"!sort", "!page", "!size"}
     * 'ResponseEntity<List<Task>> readAllTasks(){' zmienione na 'CompletableFuture<ResponseEntity<List<Task>>> readAllTasks(){' - nie używamy tego tutaj!
     */
    @GetMapping( value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Attention! GetMapping is working!");
        return ResponseEntity.ok(repository.findAll());
    }

    //pokazanie asynchroniczności
    //wykorzystujemy klasę TaskService
    /*
    @GetMapping( value = "/tasks", params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<List<Task>>> readAllTasks(){
        logger.warn("Attention! GetMapping is working!");
        return service.findAllAsync().thenApply(ResponseEntity::ok);//jak znajdziemy wynik to możemy go owinąć w Response...
    }
    */

    /**
     * Dostajemy dodatkowe informacje o stronnicowaniu - Pageable
     * @param page - jak się pojawi page to nam przekaże param do metody
     * @return
     */
    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.info("Uwaga pageable!");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id){
        return repository.findById(id).
                map(task -> {
                    return ResponseEntity.ok(task);
                }).
                orElse(ResponseEntity.notFound().build());
    }

    //lesson 130
    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(
                repository.findByDone(state)
        );
    }

    /**
     * @PathVariable - pobieramy zmienną z adresu - {id}
     * @Valid - uruchamiamy walidację
     * @param toUpdate
     * Złą metodą put możemy tworzyć nowe obiekty, a nie je aktualizować
     * Po usunięciu setId() musimy zmienić kod
     * @return
     */
    //@Transactional - podejście bez @Transa...
    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        //toUpdate.setId(id);//nie działa, nie ma setId()
        //repository.save(toUpdate);
        repository.findById(id)
                .ifPresent(task -> {task.updateFrom(toUpdate);
                repository.save(task);//ręcznie robimy zapis, nie w @Transa...
                });
        return ResponseEntity.noContent().build();
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("tasks/{id}")
    ResponseEntity<Task> deleteTask(@PathVariable int id){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Metoda aktualizacji, musi być public. Jak rzucisz wyjątek to baza danych nie zostanie zaktualizowana!
     * @param id
     * @return
     */
    @Transactional
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
//        throw new RuntimeException();
        logger.warn("Attention! PatchMapping is working!");
        return ResponseEntity.noContent().build();
    }

    //metoda wstrzykiwana z klasy nie używa proxy springa i mogą być problemy
    //@Transactional rozwiązuje problemy, uruchamia mechanizm wstrzykiwania springa
    //metoda nic nie robi
    @Transactional
    public void toggleNov (){
        toggleTask(1);
    }

}