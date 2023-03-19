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
    winner BIGINT NOT NULL,
    in_progress BOOLEAN,
    board  TEXT[][]
);