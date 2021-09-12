INSERT INTO wizard.AUTH_USER_DETAILS (user_id,
                                      USER_NAME,
                                      USER_KEY,
                                      first_name,
                                      last_name,
                                      email,
                                      phone_number,
                                      updated_on,
                                      created_on,
                                      enabled)
VALUES ('7a60e042-60f3-4e8b-bf73-fbbd181bb7c7',
        'admin',
        '$2a$10$dcy4rgj6ZyVY4TG/bpmqt.J2IdFz.DMNkScivddeXH3/5AnDpSQJq',
        'super',
        'user',
        'super@gmail.com',
        '89994561234',
        now(),
        now(),
        true),
       ('77f0fcde-4901-43bf-a124-96cfec5bb4d6',
        'gm_master_1',
        '$2a$10$dcy4rgj6ZyVY4TG/bpmqt.J2IdFz.DMNkScivddeXH3/5AnDpSQJq',
        'game',
        'master',
        'master@gmail.com',
        '89994561234',
        now(),
        now(),
        true);

INSERT INTO wizard.auth_user_authority (relation_uuid,
                                        users_user_id,
                                        authorities_auth_id)
VALUES (uuid_generate_v4(),
        '7a60e042-60f3-4e8b-bf73-fbbd181bb7c7',
        'cd39a1d5-d333-4d50-8768-d0ed7fd1fd42'),
       (uuid_generate_v4(),
        '77f0fcde-4901-43bf-a124-96cfec5bb4d6',
        '8fbde24e-c3ab-4c7a-9f52-b32977bac2ce');

INSERT INTO wizard.auth_authority (auth_id,
                                   role_code,
                                   role_description)
VALUES ('cd39a1d5-d333-4d50-8768-d0ed7fd1fd42',
        'ROLE_ADMIN',
        'admin role'),
       ('8fbde24e-c3ab-4c7a-9f52-b32977bac2ce',
        'ROLE_USER',
        'role');

