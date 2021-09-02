CREATE SCHEMA IF NOT EXISTS wizard;

CREATE TABLE wizard.AUTH_USER_DETAILS (
                          user_id                    UUID          NOT NULL,
                          USER_NAME             VARCHAR       not NULL,
                          USER_KEY              VARCHAR       not NULL,
                          first_name            VARCHAR       not NULL,
                          last_name             VARCHAR       not NULL,
                          email                 VARCHAR       not NULL,
                          phone_number          VARCHAR       not NULL,
                          updated_on            TIMESTAMPTZ NOT NULL default now(),
                          created_on            TIMESTAMPTZ NOT NULL default now(),
                          enabled               boolean     NOT NULL default 'true',
                          CONSTRAINT user_pk PRIMARY KEY (user_id)
);

CREATE TABLE wizard.AUTH_AUTHORITY (
                                         auth_id UUID not null,
                                         roleCode VARCHAR(20) not null,
                                         roleDescription VARCHAR,
                                         CONSTRAINT wizard_authority_pk PRIMARY KEY (auth_id)
);

CREATE TABLE wizard.AUTH_USER_AUTHORITY (
                                       user_id UUID not null,
                                       auth_id UUID not null,
                                       CONSTRAINT user_id_unique UNIQUE (user_id),
                                       CONSTRAINT auth_id_unique UNIQUE (auth_id)
);

ALTER TABLE ONLY wizard.AUTH_USER_DETAILS
    ADD CONSTRAINT fk_wizard_AUTH_USER_DETAILS_to_AUTH_USER_AUTHORITY
    FOREIGN KEY (user_id)
    REFERENCES wizard.AUTH_USER_AUTHORITY (user_id);

ALTER TABLE ONLY wizard.AUTH_AUTHORITY
    ADD CONSTRAINT fk_wizard_AUTH_AUTHORITY_to_AUTH_USER_AUTHORITY
    FOREIGN KEY (auth_id)
    REFERENCES wizard.AUTH_USER_AUTHORITY (auth_id);