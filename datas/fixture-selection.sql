#SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO accountancy.types (title) VALUES ('current');
INSERT INTO accountancy.types (title) VALUES ('saving');
INSERT INTO accountancy.types (title) VALUES ('credit');

INSERT INTO accountancy.currencies (title) VALUES ('CHF');
INSERT INTO accountancy.currencies (title) VALUES ('EUR');
INSERT INTO accountancy.currencies (title) VALUES ('USD');

INSERT INTO accountancy.banks (title) VALUES ('Bank A');
INSERT INTO accountancy.banks (title) VALUES ('Bank B');

INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('Account A (Bank A,CHF,saving)', 1, 1, 2);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('Account B (Bank B,USD,saving)', 2, 3, 2);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('Account C (Bank B,EUR,current)', 2, 2, 1);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('Account D (Bank B,USD,credit)', 2, 3, 3);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('Account E (Bank B,EUR,saving)', 2, 2, 2);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('Account F (Bank B,CHF,current)', 2, 1, 1);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('Account G (Bank A,EUR,credit)', 1, 2, 3);
INSERT INTO accountancy.accounts (title, bank_id, currency_id, type_id)
VALUES ('Account H (Bank B,EUR,current)', 2, 2, 1);

INSERT INTO accountancy.categories (title) VALUES ('Cat. A');
INSERT INTO accountancy.categories (title) VALUES ('Cat. B');
INSERT INTO accountancy.categories (title) VALUES ('Cat. C');

INSERT INTO accountancy.subcategories (title, category_id) VALUES ('SC. Aa', 1);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('SC. Ab', 1);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('SC. Ac', 1);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('SC. Ba', 2);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('SC. Bb', 2);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('SC. Ca', 3);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('SC. Cb', 3);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('SC. Cc', 3);
INSERT INTO accountancy.subcategories (title, category_id) VALUES ('SC. Cd', 3);

INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('001 + SC. Cb + Acc. H (Bank B,EUR,current)', -411.58, '2017-01-10', 7, 8);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('002 + SC. Bb + Acc. H (Bank B,EUR,current)', 335.91, '2017-08-06', 5, 8);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('003 + SC. Ac + Acc. H (Bank B,EUR,current)', 72.28, '2017-02-13', 3, 8);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('004 + SC. Ca + Acc. A (Bank A,CHF,saving)', -269.65, '2017-10-27', 6, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('005 + SC. Aa + Acc. F (Bank B,CHF,current)', -457.86, '2017-04-25', 1, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('006 + SC. Cc + Acc. D (Bank B,USD,credit)', -59.8, '2017-04-03', 8, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('007 + SC. Cd + Acc. A (Bank A,CHF,saving)', -218.58, '2017-06-15', 9, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('008 + SC. Ba + Acc. F (Bank B,CHF,current)', 431.59, '2017-07-22', 4, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('009 + SC. Ba + Acc. G (Bank A,EUR,credit)', 718.19, '2017-11-06', 4, 7);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('010 + SC. Ca + Acc. E (Bank B,EUR,saving)', -4.67, '2017-06-18', 6, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('011 + SC. Cc + Acc. F (Bank B,CHF,current)', 55.38, '2017-02-16', 8, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('012 + SC. Cd + Acc. H (Bank B,EUR,current)', -61.08, '2017-04-08', 9, 8);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('013 + SC. Ab + Acc. F (Bank B,CHF,current)', -530.45, '2017-05-12', 2, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('014 + SC. Cb + Acc. F (Bank B,CHF,current)', 761.84, '2017-07-19', 7, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('015 + SC. Bb + Acc. D (Bank B,USD,credit)', 37.73, '2017-08-15', 5, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('016 + SC. Ba + Acc. D (Bank B,USD,credit)', 118.49, '2017-12-11', 4, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('017 + SC. Ac + Acc. A (Bank A,CHF,saving)', -463.75, '2017-08-07', 3, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('018 + SC. Cc + Acc. B (Bank B,USD,saving)', 525.48, '2017-10-25', 8, 2);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('019 + SC. Cd + Acc. B (Bank B,USD,saving)', 112.14, '2017-01-10', 9, 2);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('020 + SC. Bb + Acc. A (Bank A,CHF,saving)', 589.84, '2017-10-12', 5, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('021 + SC. Cb + Acc. C (Bank B,EUR,current)', 343.18, '2017-06-14', 7, 3);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('022 + SC. Cc + Acc. D (Bank B,USD,credit)', -203.65, '2017-10-26', 8, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('023 + SC. Ba + Acc. A (Bank A,CHF,saving)', 16.36, '2017-11-26', 4, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('024 + SC. Aa + Acc. C (Bank B,EUR,current)', -4.64, '2017-08-15', 1, 3);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('025 + SC. Cb + Acc. E (Bank B,EUR,saving)', -191.62, '2017-09-07', 7, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('026 + SC. Ac + Acc. B (Bank B,USD,saving)', 157.36, '2017-08-15', 3, 2);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('027 + SC. Cd + Acc. A (Bank A,CHF,saving)', 27.34, '2017-02-06', 9, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('028 + SC. Ca + Acc. F (Bank B,CHF,current)', -521.19, '2017-08-27', 6, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('029 + SC. Ab + Acc. D (Bank B,USD,credit)', 288.21, '2017-07-24', 2, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('030 + SC. Ca + Acc. C (Bank B,EUR,current)', -216.62, '2017-02-13', 6, 3);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('031 + SC. Ca + Acc. D (Bank B,USD,credit)', -331.64, '2017-02-21', 6, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('032 + SC. Aa + Acc. A (Bank A,CHF,saving)', -12.32, '2017-12-14', 1, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('033 + SC. Cb + Acc. B (Bank B,USD,saving)', -536.54, '2017-04-12', 7, 2);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('034 + SC. Cb + Acc. D (Bank B,USD,credit)', 179.08, '2017-12-19', 7, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('035 + SC. Ab + Acc. B (Bank B,USD,saving)', -216.54, '2017-08-02', 2, 2);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('036 + SC. Cb + Acc. G (Bank A,EUR,credit)', 58.61, '2017-06-19', 7, 7);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('037 + SC. Cc + Acc. F (Bank B,CHF,current)', 553.3, '2017-11-19', 8, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('038 + SC. Ab + Acc. D (Bank B,USD,credit)', -185.34, '2017-05-15', 2, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('039 + SC. Ac + Acc. D (Bank B,USD,credit)', 41.09, '2017-11-25', 3, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('040 + SC. Cb + Acc. E (Bank B,EUR,saving)', -58.67, '2017-08-26', 7, 5);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('041 + SC. Bb + Acc. F (Bank B,CHF,current)', 521.58, '2017-02-26', 5, 6);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('042 + SC. Ba + Acc. B (Bank B,USD,saving)', -523.22, '2017-05-19', 4, 2);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('043 + SC. Bb + Acc. A (Bank A,CHF,saving)', 17.08, '2017-07-08', 5, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('044 + SC. Cc + Acc. D (Bank B,USD,credit)', -269.97, '2017-09-04', 8, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('045 + SC. Cb + Acc. G (Bank A,EUR,credit)', -15.97, '2017-03-18', 7, 7);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('046 + SC. Ab + Acc. C (Bank B,EUR,current)', -320.4, '2017-04-22', 2, 3);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('047 + SC. Aa + Acc. D (Bank B,USD,credit)', 492.63, '2017-12-24', 1, 4);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('048 + SC. Aa + Acc. A (Bank A,CHF,saving)', -104.16, '2017-08-19', 1, 1);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('049 + SC. Bb + Acc. B (Bank B,USD,saving)', 236.95, '2017-12-08', 5, 2);
INSERT INTO accountancy.transactions (title, amount, date, sub_category_id, account_id) VALUES
  ('050 + SC. Aa + Acc. C (Bank B,EUR,current)', 105.53, '2017-03-02', 1, 3);


SET FOREIGN_KEY_CHECKS = 1;