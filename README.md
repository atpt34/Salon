# Beauty Salon

### Project description:

17. Scheduling system of the beauty salon. You need to create a page where the schedule of the salon masters is displayed, so that you can make an record. Only admin and master should see the records of clients, others see only the occupied place. After the provision of services, the client leaves a comment. The proposal for writing a review comes to an email. The admin can read comments about masters.

### Installation instructions:

1. Setup https://www.postgresql.org/ database version 9.3
2. Create project schema [pgsql_schema.sql](src/main/resources/pgsql_schema.sql)

### Setup instructions:

1. Install java 8+ https://www.oracle.com/technetwork/java/javase/downloads/index.html
2. Install maven 3.6+ https://maven.apache.org/install.html
3. In project directory open terminal
4. Build project "mvn clean package"
5. Enter command "mvn tomcat7:run"
6. Open browser and follow the link http://localhost:8888/


