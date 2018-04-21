SET foreign_key_checks = 0;

CREATE TABLE csv_remember
(
  id        INT AUTO_INCREMENT
    PRIMARY KEY,
  dateField VARCHAR(10)    NOT NULL,
  textField VARCHAR(255)   NOT NULL,
  amount    DECIMAL(10, 2) NOT NULL,
  entry_id  INT            NOT NULL,
  CONSTRAINT csv_remember_id_uindex
  UNIQUE (id)
);

ALTER TABLE csv_remember
  ADD CONSTRAINT csv_remember_entries_id_fk
FOREIGN KEY (entry_id) REFERENCES transactions (id);

SET foreign_key_checks = 1;
