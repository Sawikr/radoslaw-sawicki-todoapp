package com.example.todoapp.controller;

import com.example.todoapp.logic.TaskService;
import com.example.todoapp.mod.Task;
import com.example.todoapp.mod.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test integracyjny, nie będzie wstawiać serwera
 * Lekki test integracyjny
 */

//jak nie wstawiamy webEnvironment to domyślnie jest wstawione WebEnvironment.MOCK
//@SpringBootTest //nie potrzebujemy adnotacji wstawiającej całego Springa
@ActiveProfiles("integration")
@WebMvcTest(TaskControler.class)
//@AutoConfigureMockMvc //testujemy, ale bez wstawiania całego serwera, odpyta warstwę webową aplikacji //też nie potrzebujemy
public class TaskControllerLightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    //@Autowired
    @MockBean //nowa sztuczka, teraz możemy mockować z Mockito //warstwa pomiędzy Mockito a Springiem
    private TaskRepository repo;

    /**
     * @throws Exception
     */
    @Test
    void httpGet_returnsGivenTask () throws Exception {

        //given
        String description = "foo";
        when(repo.findById(anyInt()))
                .thenReturn(Optional.of(new Task(description, LocalDateTime.now())));

        //when + then
        //MockMvcRequestBuilders.get() zamienieniamy na statyczny import
        mockMvc.perform(get("/tasks/123"))
                .andDo(print()) //metoda drukująca
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(description)));
    }
}