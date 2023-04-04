-- Preconfigure the database with the supported markets.
INSERT INTO markets (id, name) VALUES ('XNAS', 'Nasdaq');
INSERT INTO markets (id, name) VALUES ('XNYS', 'New York Stock Exchange (NYSE)');
INSERT INTO markets (id, name) VALUES ('XLON', 'London Stock Exchange (LSE)');

-- Preconfigure the database with the supported equities.
INSERT INTO equities (id, market) VALUES ('AAPL', 'XNAS');
INSERT INTO equities (id, market) VALUES ('TSLA', 'XNAS');
INSERT INTO equities (id, market) VALUES ('MSFT', 'XNAS');

-- Preconfigure the database with a set of scoreboard values.
INSERT INTO scoreboards (id, equity_id, date) VALUES (1, 'TSLA', '2023-03-26');
INSERT INTO scoreboards (id, equity_id, date) VALUES (2, 'TSLA', '2023-03-27');
INSERT INTO scoreboards (id, equity_id, date) VALUES (3, 'TSLA', '2023-03-28');