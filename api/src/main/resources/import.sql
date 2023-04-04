-- Preconfigure the database with the supported markets.
INSERT INTO equity_markets (id, name) VALUES ('XNAS', 'Nasdaq');
INSERT INTO equity_markets (id, name) VALUES ('XNYS', 'New York Stock Exchange (NYSE)');
INSERT INTO equity_markets (id, name) VALUES ('XLON', 'London Stock Exchange (LSE)');

-- Preconfigure the database with the supported equities.
INSERT INTO equities (ticker, market_id, name) VALUES ('AAPL', 'XNAS', 'Apple Inc.');
INSERT INTO equities (ticker, market_id, name) VALUES ('TSLA', 'XNAS', 'Tesla Inc.');
INSERT INTO equities (ticker, market_id, name) VALUES ('MSFT', 'XNAS', 'Microsoft Corporation');

-- Preconfigure the database with a set of scoreboard values.
INSERT INTO equity_scores (uuid, equity_id, date) VALUES ('41fccd33-bd77-4adc-a07f-2752901d875f', 'TSLA', '2023-03-26');
INSERT INTO equity_scores (uuid, equity_id, date) VALUES ('6c234d85-46c8-44a4-b376-9b039f78dc75', 'TSLA', '2023-03-27');
INSERT INTO equity_scores (uuid, equity_id, date) VALUES ('b34ba71c-1e26-4190-b2f5-20e3f520ecdb', 'TSLA', '2023-03-28');