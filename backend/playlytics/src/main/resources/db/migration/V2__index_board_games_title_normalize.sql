CREATE INDEX ix_boardgame_title_normalize
ON board_games (LOWER(REGEXP_REPLACE(TRIM(game_title), '\s+', ' ', 'g')));