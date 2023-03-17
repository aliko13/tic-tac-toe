CREATE TABLE player
(
    id     SERIAL PRIMARY KEY,
    name   TEXT       NOT NULL,
    symbol VARCHAR(1) NOT NULL
);

CREATE TABLE game
(
    id     SERIAL PRIMARY KEY,
    turn   TEXT NOT NULL,
    winner TEXT NOT NULL,
    board  TEXT[][]
);