SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM accounts;
DELETE FROM banks;
DELETE FROM categories;
DELETE FROM currencies;
DELETE FROM entries;
DELETE FROM types;
DELETE FROM subcategories;
DELETE FROM compound_selections_selections;
DELETE FROM compound_selections;
DELETE FROM selections;
DELETE FROM selections_accounts;
DELETE FROM selections_banks;
DELETE FROM selections_types;
DELETE FROM selections_categories;
DELETE FROM selections_sub_categories;
DELETE FROM selections_currencies;
DELETE FROM stats;
DELETE FROM csv_remember;

SET FOREIGN_KEY_CHECKS = 1;