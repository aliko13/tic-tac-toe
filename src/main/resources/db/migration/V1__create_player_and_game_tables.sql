CREATE TABLE player
(
    id     SERIAL PRIMARY KEY,
    created_date TIMESTAMP NOT NULL,
    name   TEXT       NOT NULL,
    symbol VARCHAR(1) NOT NULL
);

CREATE TABLE game
(
    id     SERIAL PRIMARY KEY,
    created_date TIMESTAMP NOT NULL,
    turn   TEXT,
    winner BIGINT,
    in_progress BOOLEAN DEFAULT true,
    board  TEXT[][]
);

-- Add foreign key constraint to map the relationship between Game and Player
ALTER TABLE player ADD COLUMN game_id BIGINT;
ALTER TABLE player ADD CONSTRAINT fk_player_game_id FOREIGN KEY (game_id) REFERENCES game(id);

-- Add foreign key constraint to map the relationship between Game and Player
ALTER TABLE game ADD CONSTRAINT fk_game_winner_id FOREIGN KEY (winner) REFERENCES player(id);