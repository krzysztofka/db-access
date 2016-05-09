
  DB Access
  =========

  What is it?
  -----------

  DB Access is a simple Spring boot application created to test 
  and practice various problems related to database access.

  Requirements
  ------------

  Java 1.8

  Maven 3.x

  Installation
  ------------

  `mvn clean package` - builds spring boot executable jar

  `java -jar target/db-access-VERSION.jar` - executes database population runner

  `java -jar target/db-access-VERSION.jar DMS` - additionally starts HSQL Database manager

  `mvn verifye -P itest` runs integration tests
  
