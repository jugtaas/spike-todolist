package org.jugtaas.spike.service;

import org.jugtaas.spike.domain.Todo;
import org.jugtaas.spike.domain.TodoList;
import org.jugtaas.spike.domain.TodoStatus;

import java.util.Date;

/**
 * Created by mario on 16/10/2016.
 */
public interface TodoService {
    TodoList findAll();
    TodoList findAll(TodoStatus status);
    TodoList findAll(Date created);
    Todo findOne(String text);
    void save(Todo todo) throws Exception;
    void save(TodoList todos) throws Exception;
    boolean delete(String todo);
}
