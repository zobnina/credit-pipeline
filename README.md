# Credit Pipeline Training Project

## Services

- [X] [ms-conveyor](https://github.com/zobnina/credit-pipeline/tree/develop/ms-conveyor)
- [X] [ms-deal](https://github.com/zobnina/credit-pipeline/tree/develop/ms-deal)
- [ ] ms-document
- [ ] ms-dossier
- [ ] ms-business-flow

## Additional libs and services

- [logging-libs](https://github.com/zobnina/logging-lib)
- [ ] ms-auth
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
- Swagger (springdoc)
- JUnit5, Faker
- Postman, Jmeter
- Prometheus, SonarQube

### ms-deal

- SpringBoot, Gradle
- Spring-Kafka, FeignClient
- Lombok, MapStruct
- JPA, Hibernate, PostgreSql, Liquibase
- Swagger (springdoc)
- JUnit5, Mockito, WireMock, TestContainers, Faker
- Prometheus, Micrometer

#### ToDo

- [ ] move /document to separate service
- [ ] add scheduler with custom metrics

## DevOps

[deployment link](https://github.com/zobnina/deployment): `https://github.com/zobnina/deployment`

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

### ToDo

- [ ] move testing files to separate project
