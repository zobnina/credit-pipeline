CREATE TABLE client
(
    id               UUID    NOT NULL,
    last_name        VARCHAR NOT NULL,
    first_name       VARCHAR NOT NULL,
    middle_name      VARCHAR,
    birth_date       DATE    NOT NULL,
    email            VARCHAR,
    gender           INT,
    marital_status   INT,
    dependent_amount INT,
    passport         JSON    NOT NULL,
    employment       JSON,
    account          VARCHAR,

    PRIMARY KEY (id)
);

CREATE TABLE credit
(
    id                   UUID    NOT NULL,
    amount               DECIMAL NOT NULL,
    term                 INT     NOT NULL,
    monthly_payment      DECIMAL NOT NULL,
    rate                 DECIMAL NOT NULL,
    psk                  DECIMAL NOT NULL,
    payment_schedule     JSON    NOT NULL,
    is_insurance_enabled BOOLEAN NOT NULL,
    is_salary_client     BOOLEAN NOT NULL,
    credit_status        INT     NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE application
(
    id             UUID NOT NULL,
    client         UUID NOT NULL,
    credit         UUID,
    status         INT  NOT NULL,
    creation_date  DATE NOT NULL,
    applied_offer  JSON,
    sign_date      DATE,
    ses_code       VARCHAR,
    status_history JSON,

    PRIMARY KEY (id)
);