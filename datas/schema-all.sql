SET foreign_key_checks = 0;

DROP VIEW IF EXISTS view_accounts, view_transactions, view_transactions_structure, view_transactions_global;
DROP TABLE IF EXISTS accounts, banks, categories, currencies, transactions, types, subcategories, csv_remember;

ALTER DATABASE accountancy
CHARACTER SET utf8
COLLATE utf8_unicode_ci;

CREATE TABLE accounts
(
  id          INT AUTO_INCREMENT
    PRIMARY KEY,
  title       VARCHAR(100) NULL,
  bank_id     INT          NOT NULL,
  currency_id INT          NOT NULL,
  type_id     INT          NULL,
  CONSTRAINT accounts_id_uindex
  UNIQUE (id)
);

CREATE INDEX accounts_banks_id_fk
  ON accounts (bank_id);

CREATE INDEX accounts_currencies_id_fk
  ON accounts (currency_id);

CREATE INDEX accounts_types_id_fk
  ON accounts (type_id);

CREATE TABLE banks
(
  id    INT AUTO_INCREMENT
    PRIMARY KEY,
  title VARCHAR(30) NOT NULL,
  CONSTRAINT banks_id_uindex
  UNIQUE (id),
  CONSTRAINT banks_title_uindex
  UNIQUE (title)
);

ALTER TABLE accounts
  ADD CONSTRAINT accounts_banks_id_fk
FOREIGN KEY (bank_id) REFERENCES banks (id);

CREATE TABLE categories
(
  id    INT AUTO_INCREMENT
    PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  CONSTRAINT category_id_uindex
  UNIQUE (id),
  CONSTRAINT categories_title_uindex
  UNIQUE (title)
);

CREATE TABLE currencies
(
  id    INT AUTO_INCREMENT
    PRIMARY KEY,
  title VARCHAR(3) NOT NULL,
  CONSTRAINT currencies_id_uindex
  UNIQUE (id),
  CONSTRAINT currencies_title_uindex
  UNIQUE (title)
);

ALTER TABLE accounts
  ADD CONSTRAINT accounts_currencies_id_fk
FOREIGN KEY (currency_id) REFERENCES currencies (id);

CREATE TABLE transactions
(
  id              INT AUTO_INCREMENT
    PRIMARY KEY,
  title           VARCHAR(255)   NOT NULL,
  amount          DECIMAL(10, 2) NOT NULL,
  date            DATE           NOT NULL,
  sub_category_id INT            NOT NULL,
  account_id      INT            NOT NULL,
  CONSTRAINT transactions_id_uindex
  UNIQUE (id),
  CONSTRAINT transactions_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
);

CREATE INDEX transactions_accounts_id_fk
  ON transactions (account_id);

CREATE INDEX transactions_subcategories_id_fk
  ON transactions (sub_category_id);

CREATE TABLE subcategories
(
  id          INT AUTO_INCREMENT
    PRIMARY KEY,
  title       VARCHAR(100) NOT NULL,
  category_id INT          NOT NULL,
  CONSTRAINT subcategory_id_uindex
  UNIQUE (id),
  CONSTRAINT subcategories_category_id_title_pk
  UNIQUE (category_id, title),
  CONSTRAINT subcategory_category_id_fk
  FOREIGN KEY (category_id) REFERENCES categories (id)
);

ALTER TABLE transactions
  ADD CONSTRAINT transactions_subcategories_id_fk
FOREIGN KEY (sub_category_id) REFERENCES subcategories (id);

CREATE TABLE types
(
  id    INT AUTO_INCREMENT
    PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  CONSTRAINT types_id_uindex
  UNIQUE (id),
  CONSTRAINT types_title_uindex
  UNIQUE (title)
);

ALTER TABLE accounts
  ADD CONSTRAINT accounts_types_id_fk
FOREIGN KEY (type_id) REFERENCES types (id);

CREATE VIEW view_accounts AS
  SELECT
    `accountancy`.`accounts`.`id`          AS `id`,
    `accountancy`.`accounts`.`title`       AS `title`,
    `accountancy`.`accounts`.`bank_id`     AS `bank_id`,
    `accountancy`.`accounts`.`currency_id` AS `currency_id`,
    `accountancy`.`accounts`.`type_id`     AS `type_id`,
    `accountancy`.`banks`.`title`          AS `bank_title`,
    `accountancy`.`currencies`.`title`     AS `currency_title`,
    `accountancy`.`types`.`title`          AS `type_title`
  FROM (((`accountancy`.`accounts`
    JOIN `accountancy`.`banks` ON ((`accountancy`.`banks`.`id` = `accountancy`.`accounts`.`bank_id`))) JOIN
    `accountancy`.`currencies`
      ON ((`accountancy`.`currencies`.`id` = `accountancy`.`accounts`.`currency_id`))) JOIN `accountancy`.`types`
      ON ((`accountancy`.`types`.`id` = `accountancy`.`accounts`.`type_id`)));

CREATE VIEW view_transactions AS
  SELECT
    `accountancy`.transactions.`id`          AS `id`,
    `accountancy`.transactions.`title`       AS `title`,
    `accountancy`.transactions.`amount`      AS `amount`,
    `accountancy`.transactions.`date`        AS `date`,
    `accountancy`.`categories`.`title`       AS `category`,
    `accountancy`.`subcategories`.`title`    AS `subcategory`,
    concat(`view_accounts`.`bank_title`, ' - ', `view_accounts`.`title`, ' - ',
           `view_accounts`.`currency_title`) AS `accout`
  FROM (((`accountancy`.transactions
    LEFT JOIN `accountancy`.`subcategories`
      ON ((`accountancy`.`subcategories`.`id` = `accountancy`.transactions.`sub_category_id`))) LEFT JOIN
    `accountancy`.`categories`
      ON ((`accountancy`.`categories`.`id` = `accountancy`.`subcategories`.`category_id`))) LEFT JOIN
    `accountancy`.`view_accounts`
      ON ((`view_accounts`.`id` = `accountancy`.transactions.`account_id`)));

CREATE VIEW view_transactions_structure AS
  SELECT
    `accountancy`.transactions.`id`              AS `id`,
    `accountancy`.transactions.`title`           AS `title`,
    `accountancy`.transactions.`amount`          AS `amount`,
    `accountancy`.transactions.`date`            AS `date`,
    `accountancy`.`categories`.`id`              AS `category_id`,
    `accountancy`.transactions.`sub_category_id` AS `sub_category_id`,
    `accountancy`.transactions.`account_id`      AS `account_id`,
    `view_accounts`.`bank_id`                    AS `bank_id`,
    `view_accounts`.`currency_id`                AS `currency_id`,
    `view_accounts`.`type_id`                    AS `type_id`
  FROM (((`accountancy`.transactions
    JOIN `accountancy`.`subcategories`
      ON ((`accountancy`.`subcategories`.`id` = `accountancy`.transactions.`sub_category_id`))) JOIN
    `accountancy`.`categories`
      ON ((`accountancy`.`categories`.`id` = `accountancy`.`subcategories`.`category_id`))) JOIN
    `accountancy`.`view_accounts` ON ((`view_accounts`.`id` = `accountancy`.transactions.`account_id`)));

CREATE VIEW view_transactions_global AS
  SELECT
    `accountancy`.transactions.`id`                                                          AS `id`,
    `accountancy`.transactions.`title`                                                       AS `title`,
    `accountancy`.transactions.`amount`                                                      AS `amount`,
    `accountancy`.transactions.`date`                                                        AS `date`,
    `accountancy`.`categories`.`id`                                                          AS `category_id`,
    `accountancy`.transactions.`sub_category_id`                                             AS `sub_category_id`,
    `accountancy`.transactions.`account_id`                                                  AS `account_id`,
    `view_accounts`.`bank_id`                                                                AS `bank_id`,
    `view_accounts`.`currency_id`                                                            AS `currency_id`,
    `view_accounts`.`type_id`                                                                AS `type_id`,
    concat(`accountancy`.`categories`.`title`, ' - ', `accountancy`.`subcategories`.`title`) AS `category`,
    concat(`view_accounts`.`bank_title`, ' - ', `view_accounts`.`title`, ' - ',
           `view_accounts`.`currency_title`)                                                 AS `account`
  FROM `accountancy`.transactions
    JOIN `accountancy`.`subcategories`
      ON `accountancy`.`subcategories`.`id` = `accountancy`.transactions.`sub_category_id`
    JOIN `accountancy`.`categories` ON `accountancy`.`categories`.`id` = `accountancy`.`subcategories`.`category_id`
    JOIN `accountancy`.`view_accounts` ON `view_accounts`.`id` = `accountancy`.transactions.`account_id`;

CREATE TABLE csv_remember
(
  id             INT AUTO_INCREMENT
    PRIMARY KEY,
  dateField      VARCHAR(10)    NOT NULL,
  textField      VARCHAR(255)   NOT NULL,
  amount         DECIMAL(10, 2) NOT NULL,
  transaction_id INT            NOT NULL,
  CONSTRAINT csv_remember_id_uindex
  UNIQUE (id)
);

ALTER TABLE csv_remember
  ADD CONSTRAINT csv_remember_transactions_id_fk
FOREIGN KEY (transaction_id) REFERENCES transactions (id);

SET foreign_key_checks = 1;