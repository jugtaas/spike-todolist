#!/bin/bash

# git clone https://github.com/jugtaas/spike-todolist.git
# cd spike-todolist

# Docker images
echo "Pull alpine-nginx"
docker pull smebberson/alpine-nginx
echo "Pull alpine-tomcat"
docker pull davidcaste/alpine-tomcat
echo "Pull alpine-postgres"
docker pull kiasaki/alpine-postgres

status=`docker inspect --format='{{.State.Status}}' todosql 2> /dev/null`
if [ -z "$status" ]; then
  echo "todosql container not present"
else
  echo "todosql container status is $status"
  if [ "$status" = 'running' ]; then
    echo "Stop todosql container"
    docker stop todosql
  fi
  echo "Remove todosql container"
  docker rm todosql
fi

echo "Build or rebuild todo-postgresql image"
docker build --rm=true --file=postgres-container/Dockerfile -t todo-postgresql ./postgres-container/

echo "Run todo-postgresql image in todosql container"
docker run --name todosql -p 5432:5432 -e POSTGRES_PASSWORD=jugtaas -d todo-postgresql

status=`docker inspect --format='{{.State.Status}}' todolist 2> /dev/null`
if [ -z "$status" ]; then
  echo "todolist container not present"
else
  echo "todolist container status is $status"
  if [ "$status" = 'running' ]; then
    echo "Stop todolist container"
    docker stop todlisto
  fi
  echo "Remove todolist container"
  docker rm todolist
fi

echo "Build or rebuild org.jugtaas.spike/todolist image"
gradle todolistDocker

echo "Run todolist org.jugtaas.spike/todolist image in todolist container"
docker run --rm -p 8080:8080 --link todosql --name todolist org.jugtaas.spike/todolist
