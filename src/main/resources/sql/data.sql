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

/* Users */

INSERT INTO users (id, name, email) VALUES (1, 'user', 'user@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (2, 'admin', 'admin@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (3, 'Blair Butterworth', 'blair.butterworth.17@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (4, 'Xiaolong Chen', 'xiaolong.chen@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (5, 'Ziad Al Halabi', 'ziad.halabi.17@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (6, 'John Wilkie', 'john.wilkie.17@ucl.ac.uk');

/* Credentials */

INSERT INTO credentials (id, user_id, username, password) VALUES (1, 1, 'user', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (2, 2, 'admin', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (3, 3, 'blair', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (4, 4, 'xiaolong', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (5, 5, 'ziad', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (6, 6, 'john', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');

/* Projects */

INSERT INTO PROJECTS (id, name, description, updated, owner_id) VALUES ('project-fizzyo', 'project Fizzyo', 'project Fizzyo Description', '2018-06-20 12:34:56', 2);
INSERT INTO PROJECTS (id, name, description, updated, owner_id) VALUES ('cancer-research', 'Cancer Research Trial 4', 'Cancer Research Trial 4 Description', '2018-05-19 11:12:13', 2);
INSERT INTO PROJECTS (id, name, description, updated, owner_id) VALUES ('aids-research', 'AIDS Research', 'AIDS Research Description', '2018-04-07 10:09:08', 2);

