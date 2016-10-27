package org.jugtaas.spike.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mario on 16/10/2016.
 */
public class TodoFactory {
    private static Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));

    private static Date getDate() {
        return cal.getTime();
    }

    public static Todo createTodo(String text) {
        Todo t = new Todo();
        t.setCreated(getDate());
        t.setStatus(TodoStatus.CREATED.name());
        t.setText(text);
        return t;
    }
}
