package com.example.todoapp.logic;

import com.example.todoapp.mod.TaskGroup;
import com.example.todoapp.mod.TaskGroupRepository;
import com.example.todoapp.mod.TaskRepository;
import com.example.todoapp.mod.projection.GroupReadModel;
import com.example.todoapp.mod.projection.GroupWriteModel;
import org.springframework.web.context.annotation.RequestScope;
import java.util.List;
import java.util.stream.Collectors;

//@Service
@RequestScope
public class TaskGroupService {

    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup (GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return  repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    // daną grupę chcemy zakończyć
    public void toggleGroup(int grupID){
        if(taskRepository.existsByDoneIsFalseAndGroup_Id(grupID)){
            throw new IllegalStateException ("Group has undone task. Done all the tasks first");
        }
        TaskGroup result = repository.findById(grupID)
                .orElseThrow(() -> new IllegalStateException("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);//też trzeba dodać by zapisać!
    }

//    //niepotrzebne już!!!
//    @Autowired
//    List<String> temp(TaskGroupRepository repository){
//        //FIXME: Problem: N + 1 (SQL-ów) //napraw mnie!!!
//        return repository.findAll().stream()
//                .flatMap(taskGroup -> taskGroup.getTasks().stream())
//                .map(task -> task.getDescription())
//                .collect(Collectors.toList());
//    }
}
