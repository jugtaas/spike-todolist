package org.jugtaa.spike.model;

import java.util.Date;

/**
 * Created by mario on 16/10/2016.
 */
public class Todo {
    private String text;
    private TodoStatus status;
    private Date created;
    private Date done;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDone() {
        return done;
    }

    public void setDone(Date done) {
        this.done = done;
    }
}
