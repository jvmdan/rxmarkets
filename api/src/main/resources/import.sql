-- Preconfigure the database with the supported markets.
INSERT INTO markets (id, name) VALUES ('XNAS', 'Nasdaq');
INSERT INTO markets (id, name) VALUES ('XNYS', 'New York Stock Exchange (NYSE)');
INSERT INTO markets (id, name) VALUES ('XLON', 'London Stock Exchange (LSE)');

-- Preconfigure the database with the supported equities.
INSERT INTO equities (id, market, ticker) VALUES ('38f2aa86-f5ad-4c5e-b24e-f8714fc5eaad', 'XNAS', 'AAPL');
INSERT INTO equities (id, market, ticker) VALUES ('ba1dbce4-89b4-4905-8275-28601932bc6b', 'XNAS', 'TSLA');
INSERT INTO equities (id, market, ticker) VALUES ('88b83efd-aa9f-486d-9ad7-7e427f35913a', 'XNAS', 'MSFT');

-- Preconfigure the database with a set of scoreboard values.
INSERT INTO scoreboards (id, equity_id, date) VALUES (1, 'ba1dbce4-89b4-4905-8275-28601932bc6b', '2023-03-26');
INSERT INTO scoreboards (id, equity_id, date) VALUES (2, 'ba1dbce4-89b4-4905-8275-28601932bc6b', '2023-03-27');
INSERT INTO scoreboards (id, equity_id, date) VALUES (3, 'ba1dbce4-89b4-4905-8275-28601932bc6b', '2023-03-28');