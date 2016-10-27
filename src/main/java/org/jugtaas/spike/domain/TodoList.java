package org.jugtaas.spike.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 16/10/2016.
 */
public class TodoList {
    private List<Todo> todoList = new ArrayList<>();

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public Todo add(Todo todo) {
        if (!contains(todo)) {
            todoList.add(todo);
        }
        
        return todo;
    }

    public int size() {
        return todoList.size();
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
