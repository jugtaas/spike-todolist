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

