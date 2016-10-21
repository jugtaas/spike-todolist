package org.jugtaas.spike;

import org.jugtaas.spike.model.Todo;
import org.jugtaas.spike.model.TodoFactory;
import org.jugtaas.spike.model.TodoList;
import org.jugtaas.spike.model.TodoStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mario on 19/10/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String baseUrl = "http://localhost";

    @Test
    public void createAndGetTodoTest() {
        String serviceUri = baseUrl + ":" + port + "/todo";

        String todoText = "This is my first task";

        Todo todo = TodoFactory.createTodo(todoText);

        this.restTemplate.put(serviceUri, todo);

        ResponseEntity<TodoList> response = this.restTemplate.getForEntity(serviceUri, TodoList.class);

        TodoList myTodoList = (TodoList) response.getBody();

        assertThat(myTodoList.size()).isEqualTo(1);

        assertThat(myTodoList.getTodoList().get(0).getText()).isEqualTo(todoText);

        this.restTemplate.delete(serviceUri + "/" + todoText);
    }

    @Test
    public void updateTodoTest() {
        String serviceUri = baseUrl + ":" + port + "/todo";

        String todoText = "This is my first task";
        Todo todo = TodoFactory.createTodo(todoText);

        this.restTemplate.put(serviceUri, todo);

        todo.setStatus(TodoStatus.DONE.name());
        todo.setDone(new Date());

        ResponseEntity<Boolean> postResponse = this.restTemplate.postForEntity(serviceUri, todo,
                Boolean.class, new HashMap<>());

        assertThat(postResponse.getBody()).isTrue();

        this.restTemplate.delete(serviceUri + "/" + todoText);
    }

    @Test
    public void deleteTodoTest() {
        String serviceUri = baseUrl + ":" + port + "/todo";

        String todoText = "This is my first task";
        Todo todo = TodoFactory.createTodo(todoText);

        this.restTemplate.put(serviceUri, todo);

        this.restTemplate.delete(serviceUri + "/" + todoText);

        ResponseEntity<TodoList> response = this.restTemplate.getForEntity(serviceUri, TodoList.class);

        TodoList myTodoList = (TodoList) response.getBody();

        assertThat(myTodoList.size()).isEqualTo(0);
    }
}
