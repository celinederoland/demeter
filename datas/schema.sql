SET foreign_key_checks = 0;

DROP VIEW IF EXISTS view_accounts, view_entries, view_entries_structure;
DROP TABLE IF EXISTS accounts, banks, categories, currencies, entries, types, subcategories,
compound_selections_selections, compound_selections, selections,
selections_accounts, selections_banks, selections_types,
selections_categories, selections_sub_categories, selections_currencies,
stats, csv_remember;

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

CREATE TABLE compound_selections
(
  id    INT AUTO_INCREMENT
    PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  CONSTRAINT compound_selections_id_uindex
  UNIQUE (id)
);

CREATE TABLE compound_selections_selections
(
  id                    INT AUTO_INCREMENT
    PRIMARY KEY,
  compound_selection_id INT NOT NULL,
  selection_id          INT NOT NULL,
  CONSTRAINT compound_selections_selections_id_uindex
  UNIQUE (id),
  CONSTRAINT compound_selections_selections_compound_selections_id_fk
  FOREIGN KEY (compound_selection_id) REFERENCES compound_selections (id)
);

CREATE INDEX compound_selections_selections_compound_selections_id_fk
  ON compound_selections_selections (compound_selection_id);

CREATE INDEX compound_selections_selections_selections_id_fk
  ON compound_selections_selections (selection_id);

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

CREATE TABLE entries
(
  id              INT AUTO_INCREMENT
    PRIMARY KEY,
  title           VARCHAR(255)   NOT NULL,
  amount          DECIMAL(10, 2) NOT NULL,
  date            DATE           NOT NULL,
  sub_category_id INT            NOT NULL,
  account_id      INT            NOT NULL,
  CONSTRAINT entries_id_uindex
  UNIQUE (id),
  CONSTRAINT entries_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
);

CREATE INDEX entries_accounts_id_fk
  ON entries (account_id);

CREATE INDEX entries_subcategories_id_fk
  ON entries (sub_category_id);

CREATE TABLE selections
(
  id         INT AUTO_INCREMENT
    PRIMARY KEY,
  title      VARCHAR(100)           NOT NULL,
  positive   TINYINT(1) DEFAULT '0' NOT NULL,
  negative   TINYINT(1) DEFAULT '0' NULL,
  cumulative TINYINT(1) DEFAULT '0' NOT NULL,
  CONSTRAINT views_id_uindex
  UNIQUE (id)
);

ALTER TABLE compound_selections_selections
  ADD CONSTRAINT compound_selections_selections_selections_id_fk
FOREIGN KEY (selection_id) REFERENCES selections (id);

CREATE TABLE selections_accounts
(
  id         INT AUTO_INCREMENT
    PRIMARY KEY,
  view_id    INT                    NOT NULL,
  account_id INT                    NOT NULL,
  inverse    TINYINT(1) DEFAULT '0' NOT NULL,
  CONSTRAINT views_accounts_id_uindex
  UNIQUE (id),
  CONSTRAINT views_accounts_views_id_fk
  FOREIGN KEY (view_id) REFERENCES selections (id),
  CONSTRAINT views_accounts_accounts_id_fk
  FOREIGN KEY (account_id) REFERENCES accounts (id)
);

CREATE INDEX views_accounts_accounts_id_fk
  ON selections_accounts (account_id);

CREATE INDEX views_accounts_views_id_fk
  ON selections_accounts (view_id);

CREATE TABLE selections_banks
(
  id      INT AUTO_INCREMENT
    PRIMARY KEY,
  view_id INT                    NOT NULL,
  bank_id INT                    NOT NULL,
  inverse TINYINT(1) DEFAULT '0' NOT NULL,
  CONSTRAINT view_banks_id_uindex
  UNIQUE (id),
  CONSTRAINT view_banks_views_id_fk
  FOREIGN KEY (view_id) REFERENCES selections (id),
  CONSTRAINT view_banks_banks_id_fk
  FOREIGN KEY (bank_id) REFERENCES banks (id)
);

CREATE INDEX view_banks_banks_id_fk
  ON selections_banks (bank_id);

CREATE INDEX view_banks_views_id_fk
  ON selections_banks (view_id);

CREATE TABLE selections_categories
(
  id          INT AUTO_INCREMENT
    PRIMARY KEY,
  view_id     INT                    NOT NULL,
  category_id INT                    NOT NULL,
  inverse     TINYINT(1) DEFAULT '0' NOT NULL,
  CONSTRAINT views_categories_id_uindex
  UNIQUE (id),
  CONSTRAINT views_categories_views_id_fk
  FOREIGN KEY (view_id) REFERENCES selections (id),
  CONSTRAINT views_categories_categories_id_fk
  FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE INDEX views_categories_categories_id_fk
  ON selections_categories (category_id);

CREATE INDEX views_categories_views_id_fk
  ON selections_categories (view_id);

CREATE TABLE selections_currencies
(
  id          INT AUTO_INCREMENT
    PRIMARY KEY,
  view_id     INT                    NOT NULL,
  currency_id INT                    NOT NULL,
  inverse     TINYINT(1) DEFAULT '0' NOT NULL,
  CONSTRAINT views_currencies_id_uindex
  UNIQUE (id),
  CONSTRAINT views_currencies_views_id_fk
  FOREIGN KEY (view_id) REFERENCES selections (id),
  CONSTRAINT views_currencies_currencies_id_fk
  FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

CREATE INDEX views_currencies_currencies_id_fk
  ON selections_currencies (currency_id);

CREATE INDEX views_currencies_views_id_fk
  ON selections_currencies (view_id);

CREATE TABLE selections_sub_categories
(
  id              INT AUTO_INCREMENT
    PRIMARY KEY,
  view_id         INT                    NOT NULL,
  sub_category_id INT                    NOT NULL,
  inverse         TINYINT(1) DEFAULT '0' NOT NULL,
  column_5        INT                    NULL,
  CONSTRAINT views_sub_categories_id_uindex
  UNIQUE (id),
  CONSTRAINT views_sub_categories_views_id_fk
  FOREIGN KEY (view_id) REFERENCES selections (id)
);

CREATE INDEX views_sub_categories_subcategories_id_fk
  ON selections_sub_categories (sub_category_id);

CREATE INDEX views_sub_categories_views_id_fk
  ON selections_sub_categories (view_id);

CREATE TABLE selections_types
(
  id      INT AUTO_INCREMENT
    PRIMARY KEY,
  view_id INT                    NOT NULL,
  type_id INT                    NOT NULL,
  inverse TINYINT(1) DEFAULT '0' NOT NULL,
  CONSTRAINT views_types_id_uindex
  UNIQUE (id),
  CONSTRAINT views_types_views_id_fk
  FOREIGN KEY (view_id) REFERENCES selections (id)
);

CREATE INDEX views_types_types_id_fk
  ON selections_types (type_id);

CREATE INDEX views_types_views_id_fk
  ON selections_types (view_id);

CREATE TABLE stats
(
  id         INT AUTO_INCREMENT
    PRIMARY KEY,
  event_name VARCHAR(100)                        NOT NULL,
  action     VARCHAR(100)                        NOT NULL,
  time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  duration   INT                                 NULL,
  infos      VARCHAR(255)                        NULL,
  CONSTRAINT stats_id_uindex
  UNIQUE (id)
);

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

ALTER TABLE entries
  ADD CONSTRAINT entries_subcategories_id_fk
FOREIGN KEY (sub_category_id) REFERENCES subcategories (id);

ALTER TABLE selections_sub_categories
  ADD CONSTRAINT views_sub_categories_subcategories_id_fk
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

ALTER TABLE selections_types
  ADD CONSTRAINT views_types_types_id_fk
FOREIGN KEY (type_id) REFERENCES types (id);

CREATE VIEW view_accounts AS
  SELECT
    `compta`.`accounts`.`id`          AS `id`,
    `compta`.`accounts`.`title`       AS `title`,
    `compta`.`accounts`.`bank_id`     AS `bank_id`,
    `compta`.`accounts`.`currency_id` AS `currency_id`,
    `compta`.`accounts`.`type_id`     AS `type_id`,
    `compta`.`banks`.`title`          AS `bank_title`,
    `compta`.`currencies`.`title`     AS `currency_title`,
    `compta`.`types`.`title`          AS `type_title`
  FROM (((`compta`.`accounts`
    JOIN `compta`.`banks` ON ((`compta`.`banks`.`id` = `compta`.`accounts`.`bank_id`))) JOIN `compta`.`currencies`
      ON ((`compta`.`currencies`.`id` = `compta`.`accounts`.`currency_id`))) JOIN `compta`.`types`
      ON ((`compta`.`types`.`id` = `compta`.`accounts`.`type_id`)));

CREATE VIEW view_entries AS
  SELECT
    `compta`.`entries`.`id`                  AS `id`,
    `compta`.`entries`.`title`               AS `title`,
    `compta`.`entries`.`amount`              AS `amount`,
    `compta`.`entries`.`date`                AS `date`,
    `compta`.`categories`.`title`            AS `category`,
    `compta`.`subcategories`.`title`         AS `subcategory`,
    concat(`view_accounts`.`bank_title`, ' - ', `view_accounts`.`title`, ' - ',
           `view_accounts`.`currency_title`) AS `accout`
  FROM (((`compta`.`entries`
    LEFT JOIN `compta`.`subcategories`
      ON ((`compta`.`subcategories`.`id` = `compta`.`entries`.`sub_category_id`))) LEFT JOIN `compta`.`categories`
      ON ((`compta`.`categories`.`id` = `compta`.`subcategories`.`category_id`))) LEFT JOIN `compta`.`view_accounts`
      ON ((`view_accounts`.`id` = `compta`.`entries`.`account_id`)));

CREATE VIEW view_entries_structure AS
  SELECT
    `compta`.`entries`.`id`              AS `id`,
    `compta`.`entries`.`title`           AS `title`,
    `compta`.`entries`.`amount`          AS `amount`,
    `compta`.`entries`.`date`            AS `date`,
    `compta`.`categories`.`id`           AS `category_id`,
    `compta`.`entries`.`sub_category_id` AS `sub_category_id`,
    `compta`.`entries`.`account_id`      AS `account_id`,
    `view_accounts`.`bank_id`            AS `bank_id`,
    `view_accounts`.`currency_id`        AS `currency_id`,
    `view_accounts`.`type_id`            AS `type_id`
  FROM (((`compta`.`entries`
    JOIN `compta`.`subcategories` ON ((`compta`.`subcategories`.`id` = `compta`.`entries`.`sub_category_id`))) JOIN
    `compta`.`categories` ON ((`compta`.`categories`.`id` = `compta`.`subcategories`.`category_id`))) JOIN
    `compta`.`view_accounts` ON ((`view_accounts`.`id` = `compta`.`entries`.`account_id`)));

SET foreign_key_checks = 1;
