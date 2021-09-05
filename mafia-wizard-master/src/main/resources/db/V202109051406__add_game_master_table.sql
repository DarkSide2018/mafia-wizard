
CREATE TABLE wizard.game_masters (
                          game_master_uuid      UUID          NOT NULL,
                          nick_name             VARCHAR       not NULL,
                          CONSTRAINT game_masters_pk PRIMARY KEY (game_master_uuid)
);

CREATE TABLE wizard.games (
                                     game_uuid      UUID          NOT NULL,
                                     game_number    int4          not NULL default 0,
                                     players        JSON          not null,
                                     CONSTRAINT game_pk PRIMARY KEY (game_uuid)
);

