# Credit Pipeline Training Project

## Services

- [X] ms-conveyor
- [X] ms-deal
- [ ] ms-dossier
- [ ] ms-business-flow

## Additional libs and services

- https://github.com/zobnina/logging-lib
- [ ] ms-audit
- [ ] ms-credit-pipeline-ui

## Api

### ms-conveyor

- `POST`: <tt>/conveyor/offers</tt>
- `POST`: <tt>/conveyor/calculation</tt>

### ms-deal

- `POST`: <tt>/deal/application</tt> - create application and get possible offers
- `PUT`: <tt>/deal/offer</tt> - apply offer to application
- `PUT`: <tt>/deal/calculate/{applicationId}</tt> - add credit to application
- `POST`: <tt>/deal/document/{applicationId}/send</tt> - Send documents request
- `POST`: <tt>/deal/document/{applicationId}/sign</tt> - Sign documents request
- `POST`: <tt>/deal/document/{applicationId}/code</tt> - Sign documents

## Tech Stack

### ms-conveyor

- SpringBoot, Maven
- Lombok
- Swagger
- JUnit5, Faker
- Postman, Jmeter
- Docker, Kubernetes
- Prometheus, SonarQube

### ms-deal

- SpringBoot, Gradle
- Spring-Kafka, FeignClient
- Lombok, MapStruct
- JPA, Hibernate, PostgreSql, Liquibase
- Swagger
- JUnit5, Mockito, WireMock, TestContainers, Faker
- Prometheus, Micrometer

## DevOps

- [ ] docker
    - [X] ms-conveyor
    - [X] ms-deal
    - [ ] ms-dossier
    - [ ] ms-business-flow
- [ ] docker-compose
    - [X] ms-conveyor
    - [X] prometheus
    - [X] ms-deal
    - [X] postgres
    - [X] kafka, zookeeper
    - [X] sonarqube
    - [ ] ms-dossier
    - [ ] ms-business-flow
- [ ] kubernetes
    - [X] ms-conveyor
    - [X] prometheus
    - [X] ms-deal
    - [X] postgres
    - [X] kafka, zookeeper
    - [ ] sonarqube
    - [ ] ms-dossier
    - [ ] ms-business-flow
- [ ] helm

## Testing

- [ ] postman
    - [X] ms-conveyor
    - [X] ms-deal
- [ ] jmeter
    - [X] ms-conveyor
    - [ ] ms-deal
