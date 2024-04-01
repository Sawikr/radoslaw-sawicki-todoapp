package com.example.todoapp.mod.projection;

import com.example.todoapp.mod.Project;
import com.example.todoapp.mod.ProjectStep;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;

public class ProjectWriteModel {

    @NotBlank(message = "Project...")
    private String description;
    @Valid
    private List<ProjectStep> steps;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<ProjectStep> getSteps() {
        return steps;
    }

    public void setSteps(final List<ProjectStep> steps) {
        this.steps = steps;
    }

    public Project toProject() {
        var result = new Project();
        result.setDescription(description);
        steps.forEach(steps->steps.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}