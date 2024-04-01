package com.example.todoapp.controller;

import com.example.todoapp.mod.Task;
import com.example.todoapp.mod.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tworzymy testy EndTwoEnd - E2E
 * Test odpala bazę danych, migrację, odpytujemy bazę danych i robimy coś z wynikiem!
 * Zmieniłem application.yml na application-local.yml i zadziałał PRAWIE!
 */

//po pierwszym odpaleniu widzi dwie rzeczy, więc musimy dodać @Primary
//dodajemy @Primary w TestConfiguration
//@ActiveProfiles("integration")//wskazujemy bazę, którą stworzyliśmy, a nie tą produkcyjną; repozytorium trzyma wszystko w pamięci
//usuwamy @ActiveProfiles bo chcemy zrobić to jak należy - lesson 121
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTestE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    TaskRepository repo;

    //Test odczytujący nasze taski
    @Test
    void httpGet_returnsAllTasks(){
        //given
        //musimy zrobić bardziej defensywnie, bo mamy jedną migrację, która nam wstawia i się test crashing!
        int initial = repo.findAll().size();
        //jak usuniemy to test przechodzi, bo jest pewnie błąd: wstawianie do bazy!
        //BŁĄD ZNALEZIONY: w konstruktorze Task nie było: this.description = description; this.deadline = deadline;
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));


        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);//wersja oszukana z tablicą Task

        //then
        //rozmiar musi być o 2 większy
        assertThat(result).hasSize(initial + 2);
    }
}