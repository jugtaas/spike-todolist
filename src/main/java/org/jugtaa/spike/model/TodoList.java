package org.jugtaa.spike.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by mario on 16/10/2016.
 */
public class TodoList {
    private List<Todo> todoList;

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public boolean contains(Todo todo) {
        for (Todo t : todoList) {
            if (todo.getText().equals(t.getText())) {
                return true;
            }
        }

        return false;
    }
}
