SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM accounts;
DELETE FROM banks;
DELETE FROM categories;
DELETE FROM currencies;
DELETE FROM transactions;
DELETE FROM types;
DELETE FROM subcategories;
DELETE FROM csv_remember;

SET FOREIGN_KEY_CHECKS = 1;