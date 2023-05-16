CREATE SCHEMA transaction;
CREATE TABLE transaction.transaction
(
    id                SERIAL PRIMARY KEY,
    sending_principal NUMERIC NOT NULL,
    payout_principal  NUMERIC NOT NULL,
    fees              NUMERIC NOT NULL,
    commission        NUMERIC NOT NULL,
    agent_commission  NUMERIC NOT NULL,
    sender_id         INTEGER NOT NULL,
    beneficiary_id    INTEGER NOT NULL,
    status            VARCHAR(10)
);