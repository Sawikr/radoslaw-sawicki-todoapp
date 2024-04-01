package com.example.todoapp.controller;

import com.example.todoapp.TaskConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoControler {

    // Wstrzykujemy construktory
    private DataSourceProperties dataSource;
    //@Value("${task.allowMultipleTasksFromTemplate}")
    private TaskConfigurationProperties myProp;

    //myProp - błąd kompilatora - trzeba dodać do TaskConfig... @Configuration
    public InfoControler(DataSourceProperties dataSource, TaskConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/info/url")
    String url(){
        return dataSource.getUrl();
    }

    @GetMapping("/info/prop")
    boolean myProp(){
        return myProp.isAllowMultipleTasksFromTemplate();
    }

}
