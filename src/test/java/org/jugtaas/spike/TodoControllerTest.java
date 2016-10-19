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
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
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
    public void createTodoTest() {
        String serviceUri = baseUrl + ":" + port + "/todo";

        Todo todo = TodoFactory.createTodo("This is my first task");

        this.restTemplate.put(serviceUri, todo);

        ResponseEntity<TodoList> response = this.restTemplate.getForEntity(serviceUri, TodoList.class);

        TodoList myTodoList = (TodoList) response.getBody();

        assertThat(myTodoList.getTodoList().size()).isEqualTo(1);

        System.out.println("todos: #" + myTodoList.getTodoList().size());
    }

    @Test
    @DependsOn(value = "createTodoTest")
    public void updateTodoTest() {
        String serviceUri = baseUrl + ":" + port + "/todo";

        Todo todo = TodoFactory.createTodo("This is my first task");
        todo.setStatus(TodoStatus.DONE);
        todo.setDone(new Date());

        ResponseEntity<Boolean> postResponse = this.restTemplate.postForEntity(serviceUri, todo, Boolean.class, new HashMap<>());

        assertThat(postResponse.getBody()).isTrue();
        
        ResponseEntity<TodoList> response = this.restTemplate.getForEntity(serviceUri, TodoList.class);

        TodoList myTodoList = (TodoList) response.getBody();

        assertThat(myTodoList.getTodoList().size()).isEqualTo(1);

        System.out.println("todos: #" + myTodoList.getTodoList().size());
    }

    @Test
    public void getTodoListTest() {
        String serviceUri = baseUrl + ":" + port + "/todo";

        Todo todo = TodoFactory.createTodo("This is my first task");

        this.restTemplate.put(serviceUri, todo);

        ResponseEntity<TodoList> response = this.restTemplate.getForEntity(serviceUri, TodoList.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        TodoList myTodoList = (TodoList) response.getBody();

        assertThat(myTodoList.getTodoList().size()).isEqualTo(1);
    }

    @Test
    public void deleteTodoTest() {
        String serviceUri = baseUrl + ":" + port + "/todo";

        this.restTemplate.delete(serviceUri + "/This is my first task");

        ResponseEntity<TodoList> response = this.restTemplate.getForEntity(serviceUri, TodoList.class);

        TodoList myTodoList = (TodoList) response.getBody();

        assertThat(myTodoList.getTodoList().size()).isEqualTo(0);

        System.out.println("todos: #" + myTodoList.getTodoList().size());
    }
}
