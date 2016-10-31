
REM "exec 'docker run --rm -p 8080:8080 --link todosql --name todolist org.jugtaas.spike/todolist'"

docker run --rm -p 8080:8080 --link todosql --name todolist org.jugtaas.spike/todolist
