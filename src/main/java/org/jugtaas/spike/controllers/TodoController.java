package org.jugtaas.spike.controllers;

import org.jugtaas.spike.model.Todo;
import org.jugtaas.spike.model.TodoList;
import org.jugtaas.spike.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mario on 13/10/2016.
 */
@RestController
@ComponentScan(basePackages = {"org.jugtaas.spike"})
public class TodoController {
    @Autowired
    private TodoService srv;

    public TodoController(TodoService srv) {
        this.srv = srv;
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
    public Boolean update(@RequestBody Todo todo) {
        return save(todo);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/todo/{text}")
    public Boolean delete(@PathVariable("text") String text) {
        return srv.delete(text);
    }
}
