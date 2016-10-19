package org.jugtaa.spike.controllers;

import org.jugtaa.spike.model.Todo;
import org.jugtaa.spike.model.TodoFactory;
import org.jugtaa.spike.model.TodoList;
import org.jugtaa.spike.model.TodoStatus;
import org.jugtaa.spike.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by mario on 13/10/2016.
 */
@RestController
public class TodoController {
    @Autowired
    private TodoService srv;

    public TodoController(TodoService srv) {
        this.srv = srv;

        try {
            srv.save(TodoFactory.createTodo("This is my first task"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean save(Todo todo) {
        try {
            srv.save(todo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todo")
    public TodoList listAll() {
        return srv.findAll();
    }

/*    @RequestMapping(method = RequestMethod.GET, value = "/todo/status/{status}")
    public TodoList listFilterByStatus(@PathVariable(required = false) TodoStatus status) {
        System.out.println("list method " + status);

        if (status != null) {
            return srv.findAll(status);
        }

        return srv.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todo/created/{created}")
    public TodoList listFilterByDate(@PathVariable(required = false) Long created) {
        System.out.println("list method " + created);

        if (created != null) {
            return srv.findAll(new Date(created));
        }

        return srv.findAll();
    }*/

    @RequestMapping(method = RequestMethod.PUT,
            value = "/todo",
            consumes = "application/json")
    public boolean create(@RequestBody Todo todo) {
        return save(todo);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/todo")
    public boolean update(@RequestBody Todo todo) {
        return save(todo);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/todo/{text}")
    public boolean delete(@PathVariable("text") String text) {
        return srv.delete(text);
    }
}
