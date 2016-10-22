package org.jugtaas.spike.service;

import org.apache.log4j.Logger;
import org.jugtaas.spike.model.Todo;
import org.jugtaas.spike.model.TodoList;
import org.jugtaas.spike.model.TodoRepository;
import org.jugtaas.spike.model.TodoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Created by mario on 20/10/2016.
 */
@Service(value = "TodoService")
@PropertySource("classpath:/config/application.properties")
public class JpaTodoServiceImpl implements TodoService {

    private static final Logger LOG = Logger.getLogger(JpaTodoServiceImpl.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    TodoRepository todoRepository;

    @Override
    public TodoList findAll() {
        final TodoList todoList = new TodoList();

        final Iterable<Todo> todos = todoRepository.findAll();

        todos.forEach(todo -> todoList.add(todo));

        return todoList;
    }

    @Override
    public TodoList findAll(TodoStatus status) {
        if (LOG.isWarnEnabled()) {
            LOG.warn("Method not yet implemented status " + status.name());
        }
        return findAll();
    }

    @Override
    public TodoList findAll(Date created) {
        if (LOG.isWarnEnabled()) {
            LOG.warn("Method not yet implemented status " + created);
        }
        return findAll();
    }

    @Override
    public Todo findOne(String text) {
        List<Todo> todoFound = todoRepository.findByText(text);
        // TODO: 21/10/2016 Have to check the number of results, should be unique
        Todo todo = null;
        if (todoFound.size() > 0) {
            todo = todoFound.get(0);
        }
        return todo;
    }

    @Override
    @Transactional
    public void save(Todo todo) throws Exception {
        if (todo.getId() == null) {
            Todo dbTodo = findOne(todo.getText());
            if (dbTodo == null) {
                entityManager.persist(todo);
            } else {
                dbTodo.setStatus(todo.getStatus());
                dbTodo.setDone(todo.getDone());
                entityManager.merge(dbTodo);
            }
        } else {
            entityManager.merge(todo);
        }
    }

    @Override
    public void save(TodoList todos) throws Exception {
        todos.getTodoList()
                .stream()
                .forEach(todo -> {
                    try {
                        save(todo);
                    } catch(Exception e) {
                        LOG.error(e.getMessage());
                    }
                });
    }

    @Override
    @Transactional
    public boolean delete(String todo) {
        if (LOG.isInfoEnabled()) {
            LOG.info("I'm going to delete |" + todo + "|");
        }
        Todo todoFound = findOne(todo);
        if (todoFound != null) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Found todo #" + todoFound.getId());
            }
            entityManager.remove(todoFound);

            return true;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("The todo " + todo + " is not going to be removed!");
        }
        return false;
    }

    public TodoRepository getTodoRepository() {
        return todoRepository;
    }

    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
}
