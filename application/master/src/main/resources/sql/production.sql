/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 *
 * Author: Blair Butterworth
 * Author: John Wilkie
 */

/* Accounts */
INSERT IGNORE INTO users (id, name, email, image) VALUES (1, 'user', 'user@ucl.ac.uk', 'pp_1.jpg');
INSERT IGNORE INTO users (id, name, email, image) VALUES (2, 'admin', 'admin@ucl.ac.uk', 'pp_4.jpg');
INSERT IGNORE INTO users (id, name, email, image) VALUES (3, 'System API', 'api@newton.com', 'default.jpg');

INSERT IGNORE INTO credentials (id, user_id, username, password, role) VALUES (1, 1, 'user@ucl.ac.uk', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'USER');
INSERT IGNORE INTO credentials (id, user_id, username, password, role) VALUES (2, 2, 'admin@ucl.ac.uk', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'ADMIN');
INSERT IGNORE INTO credentials (id, user_id, username, password, role) VALUES (3, 3, 'api@newton.com', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'API');

/* Slave configuration */
INSERT IGNORE INTO plugin (id, location) VALUES (1, 'classpath:/plugins/processor/python.jar');
INSERT IGNORE INTO plugin (id, location) VALUES (2, 'classpath:/plugins/processor/jupyter.jar');
INSERT IGNORE INTO plugin (id, location) VALUES (3, 'classpath:/plugins/data/WeatherDataProvider.jar');
INSERT IGNORE INTO plugin (id, location) VALUES (4, 'classpath:/plugins/data/FizzyoDataProvider.jar');
INSERT IGNORE INTO plugin (id, location) VALUES (5, 'classpath:/plugins/publisher/DREDataPublisher.jar');

/* Data permissions */
INSERT IGNORE INTO dataPermissions (permission_id, permission_owner, permission_ds_ident) VALUES (1, 2, 'weather.csv');
INSERT IGNORE INTO dataPermissions (permission_id, permission_owner, permission_ds_ident) VALUES (2, 2, 'heart-rate.csv');
INSERT IGNORE INTO dataPermissions (permission_id, permission_owner, permission_ds_ident) VALUES (3, 2, 'exercise-sessions.csv');
INSERT IGNORE INTO dataPermissions (permission_id, permission_owner, permission_ds_ident) VALUES (4, 2, 'games-sessions.csv');
INSERT IGNORE INTO dataPermissions (permission_id, permission_owner, permission_ds_ident) VALUES (5, 2, 'pressure-raw.csv');
INSERT IGNORE INTO dataPermissions (permission_id, permission_owner, permission_ds_ident) VALUES (6, 2, 'foot-steps.csv');
INSERT IGNORE INTO dataPermissions (permission_id, permission_owner, permission_ds_ident) VALUES (7, 2, 'foot-steps-granular.csv');