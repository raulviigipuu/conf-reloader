# conf-reloader
Java conf reloader demo, loading changes from conf file without app restart

## run

First copy conf file:

    cp src/main/resources/config.properties ./config.properties

Then execute app as described below and try to change the values in conf file.

### gradle run

Env:

    export APP_CONF=./config.properties ; ./gradlew run

Command line arg:

    ./gradlew run --args=--conf=./config.properties

### jar

    ./gradlew jar

Env:

    export APP_CONF=./config.properties ; java -jar build/libs/conf-reloader.jar

Command line arg:

    java -jar build/libs/conf-reloader.jar --conf=./config.properties


## configuration loading order

 - From environment variable (APP_CONF)
 - From command line argument(--conf)
 - From default location(resources)
