CALL mvn clean compile install -f .\Server\
CALL mvn clean compile install -f .\Client\
CALL docker-compose --project-name poc build --no-cache --pull
CALL ECHO POC ready to use. Start it with docker-compose up or by deploying on kubectl!
PAUSE
