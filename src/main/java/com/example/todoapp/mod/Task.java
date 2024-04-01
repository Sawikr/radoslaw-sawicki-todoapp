/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.todoapp.mod;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.flywaydb.core.Flyway;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Transient;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */

@Entity
@Table(name= "tasks")
//Usuwamy extends BaseAuditableEntity
public class Task {
    
    @Id
    //jak jest błąd sekwencji to zmień z AUTO na IDENTITY
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
        
    /**
     * @NotBlank - musi być org.hibernate.validator.constraints.NotBlank
     */
    //@Column(name = "desc")
    @NotBlank(message = "Task's description must be not null")
    private String description;
    
    //@Column(name = "done")
    private boolean done;

    private LocalDateTime deadline;
    // przerabia spring w sql na created_on
    // nie tworze settera i gettera, ta wartość nie ma być obiektem, ale do wyliczenia, tworzona dopiero przy zapisie w bazie danych
    //@Transient - tego pola nie chcemy zapisywać
    //private LocalDateTime createdOn;//przenosimy do BaseAuditableEntity
    //private LocalDateTime updatedOn;

    //osadzenie klasy Audit
    @Embedded
    private Audit audit = new Audit();

    //tworzymy obiekt TaskGroup
    @ManyToOne
    @JoinColumn (name = "task_group_id")
    private TaskGroup group;

    public Task() {
    }

    public Task(String description, LocalDateTime deadline){
        this(description, deadline, null);
    }

    //tutaj był Błąd, nie było this.des... i this.dead...
    public Task(String description, LocalDateTime deadline, TaskGroup group) {
        this.description = description;
        this.deadline = deadline;
        if(group != null){
            this.group = group;
        }
    }

    public int getId() {
        return id;
    }

    //chowamy tego settera
    //public void setId(int id) { this.id = id; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    //metoda - lepsze podejście
    public void updateFrom(final Task source){
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }

    public TaskGroup getGroup() {
        return group;
    }

    //nie udostepniamy na świat, tylko pakietowo
    void setGroup(TaskGroup group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", description=" + description + ", done=" + done + '}';
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    //przenosimy do BaseAuditableEntity
    //@PrePersist
    //void prePersist() {
    //    createdOn = LocalDateTime.now();
    //}

    //@PreUpdate
    //void preMerge() {
    //    updatedOn = LocalDateTime.now();
    //}
}