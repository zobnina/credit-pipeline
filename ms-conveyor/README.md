# ms-conveyor

Run service through docker: .\command.cmd

## Api

- `POST`<tt>/conveyor/offers</tt> - calculation of possible loan terms
- `POST`<tt>/conveyor/calculation</tt> - validation of sent data + data scoring + full calculation of loan parameters

## Tech Stack

- SpringBoot
- Maven
- Lombok
- Swagger
- JUnit5, Faker

## ToDo

- [ ] docker compose
- [ ] deployment config
- [ ] secret
- [ ] helm
- [ ] prometheus
- [ ] grafana
- [ ] zipkin

## Config

| key                                      | value                                     | description                                                            | example |
|:-----------------------------------------|:------------------------------------------|:-----------------------------------------------------------------------|:--------|
| server.port                              | SERVER_PORT                               | the port where the service starts                                      | 8080    |
| server.error.include-stacktrace          | SERVER_INCLUDE_STACKTRACE                 | include the "trace" attribute in errors                                | always  |
| logging.level.ru.neoflex.trainingcenter  | LOG_LEVEL                                 | logging level of base package                                          | debug   |
| conveyor.term.middle                     | CONVEYOR_TERM_MIDDLE                      | min month count for middle loan term                                   | 60      |
| conveyor.term.big                        | CONVEYOR_TERM_BIG                         | min month count for long loan term                                     | 120     |
| conveyor.amount.middle                   | CONVEYOR_AMOUNT_MIDDLE                    | min amount of money from which middle-amount-credit starts             | 500000  |
| conveyor.amount.big                      | CONVEYOR_AMOUNT_BIG                       | min amount of money from which large-amount-credit starts              | 2500000 |
| conveyor.rate.base                       | CONVEYOR_RATE_BASE                        | base loan rate                                                         | 10      |
| conveyor.rate.term.small                 | CONVEYOR_RATE_TERM_SMALL                  | base loan rate increase for small-term-credit                          | 15      |
| conveyor.rate.term.middle                | CONVEYOR_RATE_TERM_MIDDLE                 | base loan rate increase for middle-term-credit                         | 35      |
| conveyor.rate.term.big                   | CONVEYOR_RATE_TERM_BIG                    | base loan rate increase for long-term-credit                           | 55      |
| conveyor.rate.amount.small               | CONVEYOR_RATE_AMOUNT_SMALL                | base loan rate increase for small-amount-credit                        | 27      |
| conveyor.rate.amount.middle              | CONVEYOR_RATE_AMOUNT_MIDDLE               | base loan rate increase for middle-amount-credit                       | 16      |
| conveyor.rate.amount.big                 | CONVEYOR_RATE_AMOUNT_BIG                  | base loan rate increase for large-amount-credit                        | 5       |
| conveyor.rate.insurance                  | CONVEYOR_RATE_INSURANCE                   | base loan rate decrease for credit with insurance                      | 10      |
| conveyor.rate.salaryClient               | CONVEYOR_RATE_SALARY_CLIENT               | base loan rate decrease for credit for salary-client                   | 12      |
| conveyor.insurance.percent               | CONVEYOR_INSURANCE_PERCENT                | percent from loan amount to calculate insurance cost                   | 3       |
| conveyor.insurance.salaryClientPercent   | CONVEYOR_INSURANCE_SALARY_CLIENT_PERCENT  | percent from loan amount to calculate insurance cost for salary-client | 0       |

## History

### v0.0.4

- refactor and bugfix in logging + docker file

### v0.0.3

- masking

### v0.0.2

- logging

### v0.0.1

- config params table
- api, error-handling, swagger, unit-tests