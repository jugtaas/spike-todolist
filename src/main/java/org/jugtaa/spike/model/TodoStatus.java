package org.jugtaa.spike.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by mario on 16/10/2016.
 */
public enum TodoStatus {
    CREATED,
    WORK_IN_PROGRES,
    SUSPENDED,
    DONE;

    @JsonCreator
    public static TodoStatus forValue(String status) {
        if (CREATED.name().equalsIgnoreCase(status)) {
            return CREATED;
        } else if (WORK_IN_PROGRES.name().equalsIgnoreCase(status)) {
            return WORK_IN_PROGRES;
        } else if (SUSPENDED.name().equalsIgnoreCase(status)) {
            return SUSPENDED;
        } else if (DONE.name().equalsIgnoreCase(status)) {
            return DONE;
        }

        return null;
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
