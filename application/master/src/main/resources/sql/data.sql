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

INSERT INTO users (id, name, email) VALUES (1, 'user', 'user@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (2, 'admin', 'admin@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (3, 'Blair Butterworth', 'blair.butterworth.17@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (4, 'Xiaolong Chen', 'xiaolong.chen@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (5, 'Ziad Al Halabi', 'ziad.halabi.17@ucl.ac.uk');
INSERT INTO users (id, name, email) VALUES (6, 'John Wilkie', 'john.wilkie.17@ucl.ac.uk');

INSERT INTO credentials (id, user_id, username, password) VALUES (1, 1, 'user', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (2, 2, 'admin', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (3, 3, 'blair', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (4, 4, 'xiaolong', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (5, 5, 'ziad', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (6, 6, 'john', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');

/* Projects */

INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (1, 2, 'project-fizzyo', 'project Fizzyo', 'project Fizzyo Description', 'default.png', '2018-06-20 12:34:56');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (2, 2, 'cancer-research', 'Cancer Research Trial 4', 'Cancer Research Trial 4 Description', 'default.png', '2018-05-19 11:12:13');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (3, 2, 'aids-research', 'AIDS Research', 'AIDS Research Description', 'default.png', '2018-04-07 10:09:08');

/* Project membership */
INSERT INTO project_membership (project_id, user_id) VALUES (1, 1);
INSERT INTO project_membership (project_id, user_id) VALUES (2, 1);
INSERT INTO project_membership (project_id, user_id) VALUES (1, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (2, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (3, 2);

/* Project stars */
INSERT INTO project_starred (project_id, user_id) VALUES (1, 1);
INSERT INTO project_starred (project_id, user_id) VALUES (1, 2);
INSERT INTO project_starred (project_id, user_id) VALUES (2, 1);

/* Experiments */
INSERT INTO experiments (exp_id, exp_name, parentProject_id, creator_id) VALUES (1, 'Experiment 1', 1, 3);
INSERT INTO experiments (exp_id, exp_name, parentProject_id, creator_id) VALUES (2, 'Experiment 2', 1, 4);
INSERT INTO experiments (exp_id, exp_name, parentProject_id, creator_id) VALUES (3, 'Experiment 3', 1, 3);

/* Data sources */
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (1, 'Weather temp', 1, 'some/loc1');
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (2, 'Weather rain', 1, 'some/loc2');
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (3, 'Fizzyo HR', 1, 'some/loc3');
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (4, 'Fizzyo ACT use', 1, 'some/loc4');
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (5, 'Fizzyo steps', 2, 'some/loc5');

/* Experiment processes */
INSERT INTO process (proc_id, proc_repo_url, proc_initial_script, proc_engine) VALUES (1, 'https://github.com/blairisme/newton', 'test.py', 'python');

/* Insert experiment version data */
INSERT INTO versions (ver_id, ver_number, ver_name, process_id) VALUES (1, 1, 'Version 1', 1);

/* Link versions to experiments */
INSERT INTO experiment_versions (experiment_id, version_id) VALUES (1, 1);

/* Link data sources to versions */
INSERT INTO version_data_sources (ver_id, ds_id) VALUES (1, 1);
INSERT INTO version_data_sources (ver_id, ds_id) VALUES (1, 2);

/* Outcomes */
INSERT INTO outcomes (outcome_id, outcome_loc) VALUES (1, 'outcomes/v1.csv');
INSERT INTO outcomes (outcome_id, outcome_loc) VALUES (2, 'outcomes/v2.csv');

/* Link outcomes to versions */
INSERT INTO version_outcomes (ver_id, out_id) VALUES (1, 1);
INSERT INTO version_outcomes (ver_id, out_id) VALUES (1, 2);