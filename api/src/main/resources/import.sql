-- Preconfigure the database with the supported equities.
INSERT INTO equities (id, market, ticker) VALUES (1, 'XNAS', 'AAPL');
INSERT INTO equities (id, market, ticker) VALUES (2, 'XNAS', 'TSLA');
INSERT INTO equities (id, market, ticker) VALUES (3, 'XNAS', 'MSFT');

-- Preconfigure the database with a set of scoreboard values.
INSERT INTO scoreboards (id, equity_id, date) VALUES (1, 2, '2023-03-26');
INSERT INTO scoreboards (id, equity_id, date) VALUES (2, 2, '2023-03-27');
INSERT INTO scoreboards (id, equity_id, date) VALUES (3, 2, '2023-03-28');