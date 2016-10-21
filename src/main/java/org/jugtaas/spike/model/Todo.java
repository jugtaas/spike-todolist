package org.jugtaas.spike.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by mario on 16/10/2016.
 */
@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @SequenceGenerator(name="hbn_todo_id_seq", sequenceName="todo_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hbn_todo_id_seq")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "status")
    private String status;

    @Column(name = "created")
    private Date created;

    @Column(name = "done", nullable = true)
    private Date done;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
