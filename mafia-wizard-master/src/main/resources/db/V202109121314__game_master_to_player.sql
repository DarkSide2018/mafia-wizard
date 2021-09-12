CREATE TABLE wizard.game_master_to_player (
                                             relation_uuid                     UUID not null DEFAULT uuid_generate_v4(),
                                             players_player_uuid               UUID not null,
                                             game_masters_game_master_uuid     UUID not null,
                                             PRIMARY KEY (relation_uuid)
);


