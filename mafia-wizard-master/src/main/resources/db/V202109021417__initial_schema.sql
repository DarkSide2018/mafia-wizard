CREATE SCHEMA IF NOT EXISTS wizard;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE wizard.AUTH_USER_DETAILS (
                          user_id                    UUID          NOT NULL,
                          USER_NAME             VARCHAR       not NULL,
                          USER_KEY              VARCHAR       not NULL,
                          first_name            VARCHAR       not NULL,
                          last_name             VARCHAR       not NULL,
                          email                 VARCHAR       NULL,
                          phone_number          VARCHAR       NULL,
                          updated_on            TIMESTAMPTZ   NULL default now(),
                          created_on            TIMESTAMPTZ   NULL default now(),
                          enabled               boolean       NULL default 'true',
                          CONSTRAINT user_pk PRIMARY KEY (user_id)
);

CREATE TABLE wizard.AUTH_AUTHORITY (
                                         auth_id UUID not null,
                                         role_code VARCHAR(20) not null,
                                         role_description VARCHAR,
                                         CONSTRAINT wizard_authority_pk PRIMARY KEY (auth_id)
);

CREATE TABLE wizard.AUTH_USER_AUTHORITY (
                                       relation_uuid UUID not null DEFAULT uuid_generate_v4(),
                                       users_user_id UUID not null,
                                       authorities_auth_id UUID not null,
                                       PRIMARY KEY (relation_uuid),
                                       CONSTRAINT user_id_unique UNIQUE (users_user_id),
                                       CONSTRAINT auth_id_unique UNIQUE (authorities_auth_id)
);



CREATE TABLE wizard.players (
                                          player_uuid               UUID         NOT NULL DEFAULT uuid_generate_v4(),
                                          rating_id                 int4         not NULL default 0,
                                          foul_amount               int4         not NULL default 0,
                                          nick_name                 VARCHAR      NULL,
                                          points                    int4         not NULL default 0,
                                          additional_points         int4         not NULL default 0,
                                          penalties                 int4         not NULL default 0,
                                          best_move                 int4         not NULL default 0,
                                          victories                 int4         not NULL default 0,
                                          victories_percent         int4         not NULL default 0,
                                          victories_red             int4         not NULL default 0,
                                          victories_red_percent     int4         not NULL default 0,
                                          defeat_red                int4         not NULL default 0,
                                          victories_black           int4         not NULL default 0,
                                          defeat_black              int4         not NULL default 0,
                                          victories_black_percent   int4         not NULL default 0,
                                          don                       int4         not NULL default 0,
                                          sheriff                   int4         not NULL default 0,
                                          was_killed                int4         not NULL default 0,
                                          games                     int4         not NULL default 0,
                                          rating                    float8       not NULL default 0.0,
                                          updated_at                TIMESTAMPTZ  NULL default now(),
                                          created_at                TIMESTAMPTZ  NULL default now(),
                                          CONSTRAINT player_pk PRIMARY KEY (player_uuid)
);