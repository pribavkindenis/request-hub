INSERT INTO app_user (id, username, password)
VALUES (1, 'admin', 'f04e52cb47dac742c9a273737a8a388a35dcf8b018238fb28fff270b0837d52dbd6a5a7ee21fdd47'); -- admin
INSERT INTO app_user (id, username, password)
VALUES (2, 'samsepi0l', '71de7591e85af435eb03aa0de5acee235e462cc808c3d06ed57e121230d7628777018a95c608a1a9'); -- MrRobot

INSERT INTO app_user_role (app_user_id, role_id) VALUES (1, 1);
INSERT INTO app_user_role (app_user_id, role_id) VALUES (1, 2);
INSERT INTO app_user_role (app_user_id, role_id) VALUES (2, 2);

SELECT setval(pg_get_serial_sequence('app_user', 'id'), 2);