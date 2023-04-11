-- Preconfigure the database with the supported markets.
INSERT INTO equity_markets (id, name) VALUES ('XNAS', 'Nasdaq');
INSERT INTO equity_markets (id, name) VALUES ('XNYS', 'New York Stock Exchange (NYSE)');
INSERT INTO equity_markets (id, name) VALUES ('XLON', 'London Stock Exchange (LSE)');

-- Preconfigure the database with the supported equities.
INSERT INTO equities (ticker, market_id, name) VALUES ('AAPL', 'XNAS', 'Apple Inc.');
INSERT INTO equities (ticker, market_id, name) VALUES ('TSLA', 'XNAS', 'Tesla Inc.');
INSERT INTO equities (ticker, market_id, name) VALUES ('MSFT', 'XNAS', 'Microsoft Corporation');

-- Preconfigure the database with a set of scoreboard values.
INSERT INTO scoreboards (uuid, equity_id, date) VALUES ('13c0be47-5719-43a6-bb0d-e7913e175411', 'TSLA', '2023-03-26');
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('13c0be47-5719-43a6-bb0d-e7913e175411', 'INDUSTRY_SENTIMENT', 0.5829787234042554);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('13c0be47-5719-43a6-bb0d-e7913e175411', 'PRODUCTS_AND_SERVICES', 0.6346938775510205);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('13c0be47-5719-43a6-bb0d-e7913e175411', 'GROWTH_POTENTIAL', 0.6322222222222222);

INSERT INTO scoreboards (uuid, equity_id, date) VALUES ('4ae12f8c-e63d-431a-ba85-584f89a269c0', 'TSLA', '2023-03-27');
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('4ae12f8c-e63d-431a-ba85-584f89a269c0', 'INDUSTRY_SENTIMENT', 0.5836734693877551);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('4ae12f8c-e63d-431a-ba85-584f89a269c0', 'PRODUCTS_AND_SERVICES', 0.623);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('4ae12f8c-e63d-431a-ba85-584f89a269c0', 'GROWTH_POTENTIAL', 0.5936170212765958);

INSERT INTO scoreboards (uuid, equity_id, date) VALUES ('6699b97e-0097-4a07-9c6b-16693e59e798', 'TSLA', '2023-03-28');
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('6699b97e-0097-4a07-9c6b-16693e59e798', 'INDUSTRY_SENTIMENT', 0.5651291666666667);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('6699b97e-0097-4a07-9c6b-16693e59e798', 'PRODUCTS_AND_SERVICES', 0.6456521739130434);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('6699b97e-0097-4a07-9c6b-16693e59e798', 'GROWTH_POTENTIAL', 0.6026659574468085);

INSERT INTO scoreboards (uuid, equity_id, date) VALUES ('2e61c675-d62d-4585-8969-1668aee274aa', 'TSLA', '2023-03-30');
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('2e61c675-d62d-4585-8969-1668aee274aa', 'INDUSTRY_SENTIMENT', 0.582308);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('2e61c675-d62d-4585-8969-1668aee274aa', 'PRODUCTS_AND_SERVICES', 0.5805291666666667);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('2e61c675-d62d-4585-8969-1668aee274aa', 'GROWTH_POTENTIAL', 0.6744875);

INSERT INTO scoreboards (uuid, equity_id, date) VALUES ('e471a9dd-a2e8-40fe-a93f-413019cefdc9', 'TSLA', '2023-04-08');
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('e471a9dd-a2e8-40fe-a93f-413019cefdc9', 'INDUSTRY_SENTIMENT', 0.6970000000000001);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('e471a9dd-a2e8-40fe-a93f-413019cefdc9', 'PRODUCTS_AND_SERVICES', 0.693);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('e471a9dd-a2e8-40fe-a93f-413019cefdc9', 'GROWTH_POTENTIAL', 0.67946875);

INSERT INTO scoreboards (uuid, equity_id, date) VALUES ('60618400-6ba6-4bd4-b60e-f02dfbe6f90f', 'TSLA', '2023-04-09');
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('60618400-6ba6-4bd4-b60e-f02dfbe6f90f', 'INDUSTRY_SENTIMENT', 0.558695652173913);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('60618400-6ba6-4bd4-b60e-f02dfbe6f90f', 'PRODUCTS_AND_SERVICES', 0.5859574468085106);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('60618400-6ba6-4bd4-b60e-f02dfbe6f90f', 'GROWTH_POTENTIAL', 0.5782608695652174);

INSERT INTO scoreboards (uuid, equity_id, date) VALUES ('bd52f480-7339-446a-b98c-4a80f980e212', 'TSLA', '2023-04-10');
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('bd52f480-7339-446a-b98c-4a80f980e212', 'INDUSTRY_SENTIMENT', 0.5870000000000001);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('bd52f480-7339-446a-b98c-4a80f980e212', 'PRODUCTS_AND_SERVICES', 0.602);
INSERT INTO equity_scores (scoreboard_id, category, score) VALUES ('bd52f480-7339-446a-b98c-4a80f980e212', 'GROWTH_POTENTIAL', 0.616);