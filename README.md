# database-aggregation
API that is able to consume data from different databases

# to start application:
    1) Start docker
    2) Run docker compose file - docker-compose.yaml
    3) Connect your IDE to databases:
        3.1 
            Database - mydatabase
            URL - jdbc:mysql://localhost:3306/mydatabase
            Username - myuser
            Password - secret
            Port - 3306
        3.2 
            Database - postgres
            URL - jdbc:postgresql://localhost:5432/postgres
            Username - myuser
            Password - secret
            Port - 5432
    4) Run below files on databases what were configured in docker in step 2). Or run these files in your IDE(if connection to databases is configured:
        - mysql_table_generation.sql
        - postgress_table_generation.sql
    5) Call endpoint 'database-aggregation/v1/users'
        5.1 There are filtering in API. You may filter entities by all field. Example:
            http://localhost:8080/database-aggregation/v1/users?surname=surname2&name=name2&id=2
    6) If your want to add\remove database to app just change application.yaml. Add new config and restart the application