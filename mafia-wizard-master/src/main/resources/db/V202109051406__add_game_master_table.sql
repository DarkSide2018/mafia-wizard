
CREATE TABLE wizard.game_masters (
                          game_master_uuid      UUID          NOT NULL,
                          nick_name             VARCHAR       not NULL,
                          updated_at            TIMESTAMPTZ   NULL default now(),
                          created_at            TIMESTAMPTZ   NULL default now(),
                          CONSTRAINT game_masters_pk PRIMARY KEY (game_master_uuid)
);

CREATE TABLE wizard.games (
                                     game_uuid      UUID          NOT NULL,
                                     game_number    int8          not NULL default 0,
                                     players        JSON              null,
                                     updated_at     TIMESTAMPTZ       NULL default now(),
                                     created_at     TIMESTAMPTZ       NULL default now(),
                                     CONSTRAINT game_pk PRIMARY KEY (game_uuid)
);

CREATE TABLE wizard.game_to_game_masters (
                                     relation_uuid                 UUID not null DEFAULT uuid_generate_v4(),
                                     games_game_uuid               UUID not null,
                                     game_master_game_master_uuid  UUID not null,
                                     PRIMARY KEY (relation_uuid),
                                     CONSTRAINT games_game_uuid_unique               UNIQUE (games_game_uuid),
                                     CONSTRAINT game_master_game_master_uuid_unique  UNIQUE (game_master_game_master_uuid)
);