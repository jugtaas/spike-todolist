package org.jugtaa.spike.service;

import org.jugtaa.spike.model.Todo;
import org.jugtaa.spike.model.TodoList;
import org.jugtaa.spike.model.TodoStatus;

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
