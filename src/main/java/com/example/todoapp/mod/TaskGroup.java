/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.todoapp.mod;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

/**
 * Nowa encja
 * @author Admin
 */

@Entity
@Table(name= "task_groups")
//Usuwamy extends BaseAuditableEntity
public class TaskGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * @NotBlank - musi być org.hibernate.validator.constraints.NotBlank
     */
    //@Column(name = "desc")
    @NotBlank(message = "Task group's description must be not null")
    private String description;

    //@Column(name = "done")
    private boolean done;

    // przerabia spring w sql na created_on
    // nie tworze settera i gettera, ta wartość nie ma być obiektem, ale do wyliczenia, tworzona dopiero przy zapisie w bazie danych
    //@Transient - tego pola nie chcemy zapisywać
    //private LocalDateTime createdOn;//przenosimy do BaseAuditableEntity
    //private LocalDateTime updatedOn;

    //osadzenie klasy Audit - na bazie danych nie mamy, więc trzeba usunąć kod!
    //@Embedded
    //private Audit audit = new Audit();

    //mappedBy - wewnątrz każdego taska mapujemy na pole "group", które jest w Task
    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public TaskGroup() {
    }

    public int getId() {
        return id;
    }

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

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", description=" + description + ", done=" + done + '}';
    }
}