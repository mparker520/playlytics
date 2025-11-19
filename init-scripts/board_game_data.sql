CREATE TABLE board_games (
                             id INT PRIMARY KEY,
                             game_title VARCHAR(255) NOT NULL,
                             uid UUID DEFAULT gen_random_uuid()
);

INSERT INTO board_games (id, game_title, uid)
VALUES
    (1, 'Azul', gen_random_uuid()),
    (2, 'Bananagrams', gen_random_uuid()),
    (3, 'Canvas', gen_random_uuid()),
    (4,'Wingspan', gen_random_uuid());