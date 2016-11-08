##JUGTAAS: Spike Dockerize Java

Per Dockerize Java, quello che mi propongo di fare è creare uno stack minimale come segue:

**nginx - tomcat - postgres**

Utilizzando docker per metterlo in piedi e capire i vari passaggi.

Per evitare problemi propongo di arrivare con Docker preinstallato e alcune immagini già scaricate.

 * smebberson/alpine-nginx
 * davidcaste/alpine-tomcat
 * kiasaki/alpine-postgres

Una volta installato Docker per scaricare l'immagine basta:

    docker pull smebberson/alpine-nginx

Per verificare che l'immagine sia presente:

    docker images

All'incontro vi spiego perché *alpine*. :)

Altro prerequisito fondamentale è la vostra curiosità e anche la partecipazione attiva.

A differenza di una presentazione lo spike è un momento di esercizio comune, per condividere le proprie impressioni e scoperte.

Io creerò una piccola app da deployare su tomcat e da utilizzare per lo scopo, ma potete anche fare la vostra, se lo volete.

##Set-up del progetto

Una volta effettuato il clone del repository:

    git clone git@github.com:jugtaas/spike-todolist.git
    
Eseguire lo i comandi:

    cd ./spike-todolist
    ./quickstart.sh

Se si preferisce procedere manualmente step-by-step, ecco l'ordine delle operazioni:

 1. creare il container postgres dal dockerfile nella cartella postgres-container
 
    cd ./postgres-container
    docker build . --rm -t todo-postgresql

 1. eseguire il container postgres con l'immagine appena generata:
  
 Su windows con lo script .bat
 
     .\launch-pg-container.bat
    
 Su altri sistemi o con shell posix

     docker run --name todosql -p 5432:5432 -e POSTGRES_PASSWORD=jugtaas -d todo-postgresql
    
 1. a questo punto &egrave; possibile generare l'immagine della webapp via gradle:
 
        cd /path/to/spike-todolist
        gradle wrapper
        gradlew todolistDocker
    
 1. quindi eseguire il container della webapp:
 
 Su windows 
     
     .\jetty-container\launch-jetty-container.bat
     
 Su altri sistemi o con shell posix
 
     docker run --rm -p 8080:8080 --link todosql --name todolist org.jugtaas.spike/todolist
    
A questo punto l'ambiente &egrave; attivo, per accedere all'applicazione via browser se si utilizza windows o mac osx verificate l'indirizzo ip della virtual machine dove gira *Docker*:

    docmer-machine ip default
    
In genere &egrave; *192.168.99.100*, quindi utilizzate questo ip per accedere. Se utilizzate *Linux* potete collegarvi in *localhost* o *127.0.0.1* o utilizzare l'indirizzo ip della vostra macchina.

Da browser: *http://< ip >:8080/*

E dovrebbe comparire la schermata del todo vuota.

