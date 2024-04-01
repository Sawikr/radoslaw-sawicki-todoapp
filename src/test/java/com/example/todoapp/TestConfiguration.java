package com.example.todoapp;

import com.example.todoapp.mod.Task;
import com.example.todoapp.mod.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.util.*;

@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource(){
        //symulacja bazy danych, z którą się możemy łączyć
        //dodajemy flagę DB_CLOSE DELAY=-1, by baza danych się nie zamknęła, gdy ostatnie połączenia się z nią zamknie!
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "", "");
        //potrzebujemy sterownika bazy
        result.setDriverClassName("org.h2.Driver");
        return result;
    }

    @Bean
    //@Primary //to nie jest dobre rozwiązanie
    //@ConditionalOnProperty //też nie
    //@Profile({"integration", "!prod"}) //lepiej zostawić jak poniżej
    @Primary
    @Profile("integration")
    TaskRepository testRepo(){
        //klasa anonimowa z TestTaskRepository
        return new TaskRepository(){

            private Map<Integer, Task> tasks = new HashMap<>();

            @Override
            public List<Task> findAll() {
                return new ArrayList<>(tasks.values());
            }

            @Override
            public List<Task> findByDone(boolean done) {
                return null;
            }

            @Override
            public Optional<Task> findById(Integer id) {
                return Optional.ofNullable(tasks.get(id));
            }

            @Override
            public boolean existsById(Integer Id) {
                return tasks.containsKey(Id);
            }

            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(Integer groupID) {
                return false;
            }

            //błąd, bo put może zwracać null
            @Override
            public Task save(Task entity) {
                //return tasks.put(tasks.size() + 1, entity);//to może zwracać null!
                int key = tasks.size() + 1;
                //trzeba ustawić id
                try {
                    var field = Task.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                tasks.put(key, entity);
                return tasks.get(key);
            }

            @Override
            public Page<Task> findAll(Pageable page) {
                return null;
            }

            @Override
            public Task deleteById(int id) {
                return null;
            }
        };
    }
}
