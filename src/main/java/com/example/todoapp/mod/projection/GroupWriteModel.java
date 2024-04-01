package com.example.todoapp.mod.projection;

import com.example.todoapp.mod.TaskGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {
    private String description;
    private Set<GroupTaskWriteModel> tasks;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }

    //modyfikujemy metodę lesson 126
    //
    public TaskGroup toGroup() {
        var result = new TaskGroup();
        result.setDescription(description);
        result.setTasks(
                tasks.stream()
                        //.map(GroupTaskWriteModel::toTask)
                        .map(source -> source.toTask(result))
                        .collect(Collectors.toSet())
        );
        return result;
    }
}
