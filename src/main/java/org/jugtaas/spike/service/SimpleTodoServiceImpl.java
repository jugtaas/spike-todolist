package org.jugtaas.spike.service;

import org.jugtaas.spike.domain.Todo;
import org.jugtaas.spike.domain.TodoList;
import org.jugtaas.spike.domain.TodoStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mario on 16/10/2016.
 */
// @Component(value = "TodoService")
public class SimpleTodoServiceImpl implements TodoService {
    private Calendar cal;
    private TodoList todoList;

    public SimpleTodoServiceImpl() {
        cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
        todoList = new TodoList();
        todoList.setTodoList(new ArrayList<>());
    }

    @Override
    public TodoList findAll() {
        return todoList;
    }

    @Override
    public TodoList findAll(TodoStatus status) {
        TodoList nl = new TodoList();
        nl.setTodoList(new ArrayList<>());

        for (Todo t : todoList.getTodoList()) {
            if (status.equals(t.getStatus())) {
                nl.add(t);
            }
        }

        return nl;
    }

    @Override
    public TodoList findAll(Date created) {
        TodoList nl = new TodoList();
        nl.setTodoList(new ArrayList<>());

        cal.setTime(created);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date dayStart = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        Date dayEnd = cal.getTime();

        for (Todo t : todoList.getTodoList()) {
            Date todoCreated = t.getCreated();
            if (dayEnd.after(todoCreated) &&
                    dayStart.before(todoCreated)) {
                nl.add(t);
            }
        }

        return nl;
    }

    @Override
    public Todo findOne(String text) {
        Todo todo = null;

        for (Todo t : todoList.getTodoList()) {
            if (text.equals(t.getText())) {
                todo = t;
                break;
            }
        }

        return todo;
    }

    @Override
    public void save(Todo todo) throws Exception {
        if (!todoList.contains(todo)) {
            todoList.add(todo);
        } else {
            for (Todo _todo : todoList.getTodoList()) {
                if (todo.getText().equals(_todo.getText())) {
                    _todo.setStatus(todo.getStatus());
                    if (TodoStatus.DONE.equals(_todo.getStatus())) {
                        cal.setTimeInMillis(System.currentTimeMillis());
                        _todo.setDone(cal.getTime());
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void save(TodoList todos) throws Exception {
        for (Todo todo : todos.getTodoList()) {
            save(todo);
        }
    }

    private boolean delete(Todo todo) {
        if (todoList.contains(todo)) {
            return todoList.getTodoList().remove(todo);
        }

        return false;
    }

    @Override
    public boolean delete(String text) {
        for (Todo todo : todoList.getTodoList()) {
            if (text.equals(todo.getText())) {
                return delete(todo);
            }
        }

        return false;
    }
}
