# service-template
Questo repository può essere usato come base di partenza per la creazione di nuovi servizi java.

La versione java richiesta è la 16.

La configurazione prevede l'utilizzo di:
- un database PostgreSql tramite api jdbc (in alternativa è presente la configurazione r2dbc, ma è stata commentata)
- accesso a Redis
- consumer Kafka

Per la gestione delle migration dei database è stato configurato Liquibase (utilizzando un primo changelog in sql https://docs.liquibase.com/concepts/basic/sql-format.html)

Il log dell'applicazione viene scritto nella root del progetto. Il nome del log (di default service-template.log) ed il percorso di salvataggio, possono essere modificati tramite application.properties.

Sono stati abilitati gli endpoint di actuator (/metrics) a cui è stato aggiunto un endpoint per il monitoraggio del servizio tramite Prometheus

All'interno del progetto sono inoltre presenti:
- un controller di esempio
- un service
- un repository jpa ed entity associata
- un repository redis ed entity associata
- un resource per interfacciarsi ad un servizio rest
- un consumer kafka

# Dipendenze (per Windows)
- Java 16: https://download.java.net/java/GA/jdk16.0.2/d4a915d82b4c4fbb9bde534da945d746/7/GPL/openjdk-16.0.2_windows-x64_bin.zip
- PostgreSql: https://www.enterprisedb.com/postgresql-tutorial-resources-training?cid=437 (Dovrebbe essere utilizzato CitusData, ma per iniziare può andare bene anche solo Postgresql)
- Redis: https://github.com/microsoftarchive/redis/releases/tag/win-3.0.504 (per Windows è disponibile solo la versione 3.0.504)
- Kafka (opzionale): https://kafka.apache.org/quickstart

# Cosa fare dopo aver clonato il repository
1) Cambiare il nome del progetto nel file settings.gradle
2) Modificare le impostazioni del datasource (ed eventualmente di altre impostazioni) all'interno del file application.properties
3) Creare lo schema del database tramite migration di liquibase: file 0001_create_skeleton.sql (assicurarsi di aver creato il db in postgres)
4) Eventualmente rinominare la cartella del progetto da "servicetemplate" ad una di propria scelta
5) Start coding
