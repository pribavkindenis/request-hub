INSERT INTO role (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, role) VALUES (2, 'ROLE_USER');

INSERT INTO privilege (id, privilege) VALUES (1, 'CREATE_REQUEST');
INSERT INTO privilege (id, privilege) VALUES (2, 'READ_REQUEST');
INSERT INTO privilege (id, privilege) VALUES (3, 'UPDATE_REQUEST');
INSERT INTO privilege (id, privilege) VALUES (4, 'DELETE_REQUEST');

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 3);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 4);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 2);
