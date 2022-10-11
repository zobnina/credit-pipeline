# ms-deal

## Api

- `POST`: /deal/application - create application and get possible offers
- `PUT`: /deal/offer - apply offer to application
- `PUT`: /deal/calculate/{applicationId} - add credit to application
- `POST`: /deal/document/{applicationId}/send - Send documents request
- `POST`: /deal/document/{applicationId}/sign - Sign documents request
- `POST`: /deal/document/{applicationId}/code - Sign documents

## TechStack

- SpringBoot
- Spring-Kafka, FeignClient
- Gradle
- Lombok, MapStruct
- JPA, Hibernate, PostgreSql, Liquibase
- Swagger
- JUnit5, Mockito, WireMock, TestContainers, Faker
- Docker, Kubernetes
- Prometheus

## ToDo

- [ ] jmeter test
- [ ] helm
- [ ] sonarqube
- [ ] grafana
- [ ] zipkin
- [ ] audit ?
- [ ] auth ?

## History:

### 0.1.2:

- исправлены warnings от mapstruct

### v0.1.1

- FeignErrorDecoder

### v0.1.0

- add prometheus

### v0.0.3

- dockerfile
- props to variables
- init sql script
- masking

### v0.0.2

- move logging kafka to lib
- change logging lib

### v0.0.1

- add mapstruct, refactor + swagger docs
- unit-tests: replace h2 to test-containers, mockito to wiremock
- coverage
- bigDecimal validation annotation
- refactor: offer, application, calculate
- feature: credit-issued
- feature: sign documents
- send-documents logic
- send to kafka create-documents
- send to kafka finish-registration
- add document api
- move conveyor url to props
- add foreign key to model
- add sql logging
- fix unit-tests
- datetime format
- unit-test coverage
- add swagger
- logging feignclient
- add logging
- calculate logic
- offer logic
- application logic
- api, entities, models