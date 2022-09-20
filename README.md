# Credit Pipeline Training Project

## Services

- [X] ms-conveyor
- [X] ms-deal
- [ ] ms-application
- [ ] ms-dossier
- [ ] ms-business-flow
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

- SpringBoot
- Maven
- Lombok
- Swagger
- JUnit5, Faker
- Postman, Jmeter
- Docker, Kubernetes

### ms-deal

- SpringBoot
- Spring-Kafka, FeignClient
- Gradle
- Lombok, MapStruct
- JPA, Hibernate, PostgreSql, Liquibase
- Swagger
- JUnit5, Mockito, WireMock, TestContainers, Faker

## Additional lib

- https://github.com/zobnina/logging-lib

## Testing

- [ ] postman
    - [X] ms-conveyor
    - [ ] ms-deal
    - [ ] ms-application
- [ ] jmeter
    - [X] ms-conveyor
    - [ ] ms-deal
    - [ ] ms-application

## DevOps

- [ ] docker
    - [X] ms-conveyor
    - [ ] ms-deal
    - [ ] ms-application
- [ ] docker-compose
    - [X] ms-conveyor
    - [ ] ms-deal
    - [ ] ms-application
- [ ] kubernetes
    - [X] ms-conveyor
    - [ ] ms-deal
    - [ ] ms-application
- [ ] helm
