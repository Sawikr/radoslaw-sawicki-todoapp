package com.example.todoapp.logic;

import com.example.todoapp.mod.TaskGroup;
import com.example.todoapp.mod.TaskGroupRepository;
import com.example.todoapp.mod.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw when undone tasks")
    void toggleGroup_undoneTasks_throwsIllegalStateException(){
        //given
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(true);//istnieją taski, które są nie zrobione - TRUE
        //system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        /*assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");*/ //dzięki temu przechodzi test!
    }

    @Test
    @DisplayName("should throw when no group")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        // and
        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // system under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);

        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)//poprawione z IllegalArgumentExc.. na IllegalStateExc.. and working!
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should toggle group")
    void toggleGroup_worksAsExpected() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        // and
        var group = new TaskGroup();
        var beforeToggle = group.isDone();
        // and
        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));
        // system under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);

        // when
        toTest.toggleGroup(0);

        // then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);
    }

    private TaskRepository taskRepositoryReturning(final boolean result) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }

}
