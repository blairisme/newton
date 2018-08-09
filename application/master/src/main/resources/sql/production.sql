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
INSERT IGNORE INTO plugin (id, identifier, location) VALUES (1, 'newton-python', 'classpath:/plugins/processor/python.jar');
INSERT IGNORE INTO plugin (id, identifier, location) VALUES (2, 'newton-jupyter', 'classpath:/plugins/processor/jupyter.jar');
INSERT IGNORE INTO plugin (id, identifier, location) VALUES (3, 'newton-weather', 'classpath:/plugins/data/WeatherDataProvider.jar');
INSERT IGNORE INTO plugin (id, identifier, location) VALUES (4, 'newton-fizzyo', 'classpath:/plugins/data/FizzyoDataProvider.jar');