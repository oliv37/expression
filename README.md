# Expression

### Build Project (Only run unit tests)

mvn clean install

### Build Project (Run all tests, unit and integration tests)

mvn clean install -P full

### Launch Project (Development phase)

mvn spring-boot:run -Drun.profiles=dev -Dlogging.file=<path_file>

### Frameworks
* Spring Boot 1.4.3.RELEASE
* Spring Data MongoDB
* Spring Batch
* Cucumber