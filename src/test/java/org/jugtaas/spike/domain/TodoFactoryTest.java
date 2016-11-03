package org.jugtaas.spike.domain;

import org.junit.Test;
import sun.util.resources.TimeZoneNames;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mario on 03/11/2016.
 */
public class TodoFactoryTest {

    private static Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));

    private Date getFiveMinuteAgo() {
        cal.setTimeInMillis(System.currentTimeMillis());

        long nowTimestamp = cal.getTimeInMillis();
        long lessFiveMinuteTimestamp = nowTimestamp - (5 * 60000);
        cal.setTimeInMillis(lessFiveMinuteTimestamp);

        return cal.getTime();
    }

    private Date getAfterNow() {
        cal.setTimeInMillis(System.currentTimeMillis());

        long nowTimestamp = cal.getTimeInMillis();
        long afterNow = nowTimestamp + 60000;
        cal.setTimeInMillis(afterNow);

        return cal.getTime();
    }

    @Test
    public void createTodoTest() {
        String todoText = "This is a todo";
        Date fiveMinutesAgo = getFiveMinuteAgo();

        Todo td = TodoFactory.createTodo(todoText);

        assertThat(td).isNotNull();

        assertThat(td.getText()).isEqualTo(todoText);

        assertThat(td.getCreated()).isNotNull();

        Date nowTime = getAfterNow();

        assertThat(td.getCreated()).isBetween(fiveMinutesAgo, nowTime);

        assertThat(td.getStatus()).isEqualTo(TodoStatus.CREATED.name());

        assertThat(td.getDone()).isNull();
    }
}
