package org.jugtaas.spike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by mario on 13/10/2016.
 */
@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TodoApplication.class, args);
    }
}
