#SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO accountancy.types (title) VALUES ('Courant');
INSERT INTO accountancy.types (title) VALUES ('Epargne');
INSERT INTO accountancy.types (title) VALUES ('Crédit');

INSERT INTO accountancy.currencies (title) VALUES ('CHF');
INSERT INTO accountancy.currencies (title) VALUES ('EUR');
INSERT INTO accountancy.currencies (title) VALUES ('USD');

INSERT INTO accountancy.banks (title) VALUES ('B1');
INSERT INTO accountancy.banks (title) VALUES ('B2');

INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('C1', 1, 2, 2);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('C2', 1, 2, 2);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('C3', 2, 3, 2);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('C4', 1, 2, 1);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('C5', 2, 3, 1);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('C6', 1, 2, 3);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('C7', 1, 3, 3);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('C8', 2, 3, 3);

INSERT INTO accountancy.categories (title) VALUES ('Revenus');
INSERT INTO accountancy.categories (title) VALUES ('Transferts');
INSERT INTO accountancy.categories (title) VALUES ('Autres');
INSERT INTO accountancy.categories (title) VALUES ('Factures');

INSERT INTO accountancy.subcategories (title, category_id) VALUES ('Revenus', 1);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('Revenus-b', 1);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('Transferts', 2);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('Autres-A', 3);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('Autres-B', 3);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('Autres-C', 3);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('Factures-A', 4);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('Factures-B', 4);

INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('001', -22530.84, '2017-08-01', 6, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('002', 4990.46, '2017-08-01', 2, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('003', -2195.51, '2017-08-03', 3, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('004', -19060.72, '2017-08-04', 5, 8);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('005', -105574.54, '2017-08-04', 6, 7);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('006', 2760.07, '2017-08-04', 1, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('007', -145.86, '2017-08-07', 4, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('008', -6.8, '2017-08-07', 4, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('009', 2532.26, '2017-08-09', 2, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('010', -138, '2017-08-10', 7, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('011', -1649.93, '2017-08-11', 3, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('012', 1542.77, '2017-08-11', 3, 8);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('013', -170.7, '2017-08-11', 8, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('014', -57.19, '2017-08-11', 7, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('015', -132.37, '2017-08-15', 7, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('016', 2751.86, '2017-08-15', 2, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('017', -11.05, '2017-08-15', 4, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('018', -490.32, '2017-08-15', 6, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('019', 490.32, '2017-08-16', 6, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('020', -728.65, '2017-08-16', 6, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('021', 728.65, '2017-08-21', 6, 7);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('022', -178.86, '2017-08-21', 8, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('023', -208.02, '2017-08-23', 8, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('024', -91.09, '2017-08-24', 4, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('025', 3248.03, '2017-08-24', 5, 8);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('026', -30.76, '2017-08-24', 7, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('027', -161.17, '2017-08-27', 8, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('028', -136.64, '2017-08-28', 7, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('029', -283.99, '2017-08-29', 3, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('030', 283.99, '2017-08-30', 3, 2);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('031', -127.57, '2017-08-31', 3, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('032', 127.57, '2017-09-01', 3, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('033', -148.32, '2017-09-01', 3, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('034', 148.32, '2017-09-01', 3, 3);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('035', -141.23, '2017-09-01', 8, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('036', -113.06, '2017-09-05', 8, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('037', -153.16, '2017-09-06', 4, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('038', -152.81, '2017-09-07', 5, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('039', 152.81, '2017-09-07', 5, 8);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('040', -136.01, '2017-09-09', 4, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('041', -9, '2017-09-10', 8, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('042', -212.12, '2017-09-11', 7, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('043', 1499.11, '2017-09-11', 1, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('044', -490.32, '2017-09-13', 6, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('045', 490.32, '2017-09-13', 6, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('046', -74.6, '2017-09-13', 7, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('047', 4725.81, '2017-09-19', 2, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('048', -3041.13, '2017-09-19', 3, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('049', 3442.53, '2017-09-19', 3, 8);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('050', -7.93, '2017-09-19', 8, 5);


SET FOREIGN_KEY_CHECKS = 1;