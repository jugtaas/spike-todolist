package org.jugtaas.spike.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mario on 20/10/2016.
 */
@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    @Query("from Todo where text = :text")
    List<Todo> findByText(@Param("text") String text);
}
