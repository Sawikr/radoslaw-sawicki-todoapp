package com.example.todoapp.mod.projection;

import com.example.todoapp.mod.Task;
import com.example.todoapp.mod.TaskGroup;

import java.time.LocalDateTime;

public class GroupTaskWriteModel {
    private String description;
    private LocalDateTime deadline;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    //modyfikujemy lesson 126, korzystamy z nowego konstruktora Task (3 elementowego)
    Task toTask(final TaskGroup group) {
        return new Task(description, deadline, group);
    }
}
