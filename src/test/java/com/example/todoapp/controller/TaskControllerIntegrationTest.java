package com.example.todoapp.controller;

import com.example.todoapp.mod.Task;
import com.example.todoapp.mod.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test integracyjny, nie będzie wstawiać serwera
 */

//jak nie wstawiamy webEnvironment to domyślnie jest wstawione WebEnvironment.MOCK
@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc //testujemy, ale bez wstawiania całego serwera, odpyta warstwę webową aplikacji
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repo;

    /**
     * Test przejdzie jak zmienię andExpect(status().is2xxSuccessful())
     * na andExpect(status().is4xxClientError()), ponieważ nie istnieje żaden Task
     * SOLUTION: metoda put zwracała null!!!
     * @throws Exception
     */
    @Test
    void httpGet_returnsGivenTask () throws Exception {

        //given

        int id = repo.save(new Task("foo", LocalDateTime.now())).getId();

        //when + then
        //MockMvcRequestBuilders.get() zamienieniamy na statyczny import
        mockMvc.perform(get("/tasks/" + id))
                .andDo(print()) //metoda drukująca
                .andExpect(status().is2xxSuccessful());
                //.andExpect(status().is4xxClientError());
    }
}
