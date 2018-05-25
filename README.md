Sample Spring Boot REST Service with JSON Schema Validation
===========================================================

* Java Platform (JDK) 1.8
* Apache Maven 3.x
* Spring 4.x
* SpringBoot 1.x
* GitHub-fge json-schema-validator 2.2.8
* jackson-json-reference-core 0.3.0

Quick start
-----------
1. `mvn package`
2. `java -jar target/spring-boot-restful-api-1.0-SNAPSHOT.jar`
3. Point your browser to [http://localhost:8999](http://localhost:8999)
4. `curl -X POST -d '{ "customerid": "1234567", "password": "password12" , 
  "first": "Subhasis","last": "samal",
 "gender": "Male", "email": "subhasis.samal@test.com"
 }' http://localhost:8999/customer/add`
5. Refresh the page
