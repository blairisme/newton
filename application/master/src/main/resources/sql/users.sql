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
INSERT INTO users (id, name, email, image) VALUES (1, 'user', 'user@ucl.ac.uk', 'pp_1.jpg');
INSERT INTO users (id, name, email, image) VALUES (2, 'admin', 'admin@ucl.ac.uk', 'pp_4.jpg');
INSERT INTO users (id, name, email, image) VALUES (3, 'Blair Butterworth', 'blair.butterworth.17@ucl.ac.uk', 'profile.jpg');
INSERT INTO users (id, name, email, image) VALUES (4, 'Xiaolong Chen', 'xiaolong.chen@ucl.ac.uk', 'pp_2.jpg');
INSERT INTO users (id, name, email, image) VALUES (5, 'Ziad Al Halabi', 'ziad.halabi.17@ucl.ac.uk', 'pp_3.jpg');
INSERT INTO users (id, name, email, image) VALUES (6, 'John Wilkie', 'john.wilkie.17@ucl.ac.uk', 'pp_1.jpg');

INSERT INTO credentials (id, user_id, username, password, role) VALUES (1, 1, 'user@ucl.ac.uk', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'USER');
INSERT INTO credentials (id, user_id, username, password, role) VALUES (2, 2, 'admin@ucl.ac.uk', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'ADMIN');
INSERT INTO credentials (id, user_id, username, password, role) VALUES (3, 3, 'blair.butterworth.17@ucl.ac.uk', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'ADMIN');
INSERT INTO credentials (id, user_id, username, password, role) VALUES (4, 4, 'xiaolong.chen@ucl.ac.uk', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'ADMIN');
INSERT INTO credentials (id, user_id, username, password, role) VALUES (5, 5, 'ziad.halabi.17@ucl.ac.uk', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'ADMIN');
INSERT INTO credentials (id, user_id, username, password, role) VALUES (6, 6, 'john.wilkie.17@ucl.ac.uk', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'ADMIN');