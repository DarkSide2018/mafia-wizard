
CREATE TABLE wizard.game_masters (
                          game_master_uuid      UUID          NOT NULL,
                          nick_name             VARCHAR       not NULL,
                          updated_at            TIMESTAMPTZ   NULL default now(),
                          created_at            TIMESTAMPTZ   NULL default now(),
                          CONSTRAINT game_masters_pk PRIMARY KEY (game_master_uuid)
);

CREATE TABLE wizard.games (
                                     game_uuid      UUID          NOT NULL,
                                     game_master_uuid      UUID          NOT NULL,
                                     game_number    int4          not NULL default 0,
                                     players        JSON          not null,
                                     updated_at            TIMESTAMPTZ   NULL default now(),
                                     created_at            TIMESTAMPTZ   NULL default now(),
                                     CONSTRAINT game_pk PRIMARY KEY (game_uuid)
);

