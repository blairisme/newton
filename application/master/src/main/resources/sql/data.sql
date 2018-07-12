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
INSERT INTO users (id, name, email, role) VALUES (1, 'user', 'user@ucl.ac.uk', 'USER');
INSERT INTO users (id, name, email, role) VALUES (2, 'admin', 'admin@ucl.ac.uk', 'ADMIN');
INSERT INTO users (id, name, email, role) VALUES (3, 'Blair Butterworth', 'blair.butterworth.17@ucl.ac.uk', 'ADMIN');
INSERT INTO users (id, name, email, role) VALUES (4, 'Xiaolong Chen', 'xiaolong.chen@ucl.ac.uk', 'ADMIN');
INSERT INTO users (id, name, email, role) VALUES (5, 'Ziad Al Halabi', 'ziad.halabi.17@ucl.ac.uk', 'ADMIN');
INSERT INTO users (id, name, email, role) VALUES (6, 'John Wilkie', 'john.wilkie.17@ucl.ac.uk', 'ADMIN');

INSERT INTO credentials (id, user_id, username, password) VALUES (1, 1, 'user', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (2, 2, 'admin', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (3, 3, 'blair', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (4, 4, 'xiaolong', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (5, 5, 'ziad', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');
INSERT INTO credentials (id, user_id, username, password) VALUES (6, 6, 'john', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m');

/* Projects */
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (1, 2, 'project-fizzyo', 'Project Fizzyo', 'project Fizzyo Description', 'fizzyo.png', '2018-06-20 12:34:56');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (2, 2, 'uclh-mf', 'UCLH Mycosis Fungoides classification', 'Cancer Research Trial 4 Description', 'uclh.png', '2018-05-19 11:12:13');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (3, 2, 'gosh-jiro', 'GOSH Project Jiro', 'Project description ', 'default.png', '2018-04-07 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (4, 2, 'gosh-icd', 'GOSH ICD prediction', 'Project description', 'default.png', '2018-03-07 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (5, 2, 'uclh-neuroblastome', 'UCLH Neuroblastoma in under children', 'Project description', 'uclh.png', '2018-03-06 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (6, 2, 'rm-ewing-sarcoma', 'RM Ewing Sarcoma', 'Project description', 'rm.png', '2018-03-05 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (7, 2, 'ucl-indigo', 'UCL Indigo', 'Project description', 'ucl.png', '2018-02-07 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (8, 2, 'uclh-cross-cont', 'UCLH Reducing cross contamination', 'Project description', 'uclh.png', '2018-02-06 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (9, 2, 'gosh-dmd', 'GOSH Duchenne muscular dystrophy treatment', 'Project description', 'default.png', '2018-02-05 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (10, 2, 'rm-cns-lymphoma', 'RM Primary CNS Lymphoma', 'Project description', 'rm.png', '2018-01-07 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (11, 2, 'ucl-vega', 'UCL Vega', 'Project description', 'ucl.png', '2017-12-07 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (12, 2, 'ucl-camelot', 'UCL Project Camelot', 'Project description', 'ucl.png', '2017-08-07 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (13, 2, 'gosh-apollo', 'GOSH Project Apollo', 'Project description', 'default.png', '2017-07-07 10:09:08');

/* Project membership */
INSERT INTO project_membership (project_id, user_id) VALUES (1, 1);
INSERT INTO project_membership (project_id, user_id) VALUES (1, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (1, 3);
INSERT INTO project_membership (project_id, user_id) VALUES (1, 4);
INSERT INTO project_membership (project_id, user_id) VALUES (1, 5);
INSERT INTO project_membership (project_id, user_id) VALUES (2, 1);
INSERT INTO project_membership (project_id, user_id) VALUES (2, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (3, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (4, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (5, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (6, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (7, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (8, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (9, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (10, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (11, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (12, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (13, 2);

/* Project stars */
INSERT INTO project_starred (project_id, user_id) VALUES (1, 1);
INSERT INTO project_starred (project_id, user_id) VALUES (1, 2);
INSERT INTO project_starred (project_id, user_id) VALUES (2, 1);
INSERT INTO project_starred (project_id, user_id) VALUES (3, 2);
INSERT INTO project_starred (project_id, user_id) VALUES (4, 2);
INSERT INTO project_starred (project_id, user_id) VALUES (2, 2);
INSERT INTO project_starred (project_id, user_id) VALUES (8, 2);
INSERT INTO project_starred (project_id, user_id) VALUES (13, 2);

/* Data sources */
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (1, 'Weather temp', 1, 'some/loc1');
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (2, 'Weather rain', 1, 'some/loc2');
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (3, 'Fizzyo HR', 1, 'some/loc3');
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (4, 'Fizzyo ACT use', 1, 'some/loc4');
INSERT INTO datasources (ds_id, ds_name, ds_version, ds_data_location) VALUES (5, 'Fizzyo steps', 2, 'some/loc5');

/* Data processors */
INSERT INTO process (proc_id, proc_repo_url, proc_initial_script, proc_engine) VALUES (1, 'https://github.com/blairisme/newton', 'test.py', 'python');

/* Experiment process configurations */
INSERT INTO processor_configuration(id, processor_id, configuration_path) VALUES (1, 1, 'config.json');
INSERT INTO processor_configuration(id, processor_id, configuration_path) VALUES (2, 1, 'config.json');
INSERT INTO processor_configuration(id, processor_id, configuration_path) VALUES (3, 1, 'config.json');

/* Experiments */
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (1, 'experiment-1', 'HR classification', 1, 3, 1);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (2, 'experiment-2', 'Exercise level classification', 1, 4, 2);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (3, 'experiment-3', 'Weather temperature classification', 1, 3, 3);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (4, 'experiment-4', 'Weather data aggrigator', 1, 3, 2);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (5, 'experiment-5', 'HR normalisation', 1, 3, 1);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (6, 'experiment-6', 'Adherence rate classification', 1, 3, 1);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (7, 'experiment-7', 'Achievement rate classification', 1, 3, 2);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (8, 'experiment-8', 'Weather percipitation classification', 1, 3, 3);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (9, 'experiment-9', 'Weather humidity classification', 1, 3, 1);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (10, 'experiment-10', 'ACT adherence by type', 1, 3, 2);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (11, 'experiment-11', 'Impact of gamification on adherence', 1, 3, 3);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, project_id, creator_id, processor_configuration_id) VALUES (12, 'experiment-12', 'Gamification impact by game', 1, 3, 1);

/* Experiment data source configuration */
INSERT INTO experiment_datasources (experiement_id, datasource_id) VALUES (1, 1);
INSERT INTO experiment_datasources (experiement_id, datasource_id) VALUES (1, 2);
INSERT INTO experiment_datasources (experiement_id, datasource_id) VALUES (2, 3);
INSERT INTO experiment_datasources (experiement_id, datasource_id) VALUES (2, 4);
INSERT INTO experiment_datasources (experiement_id, datasource_id) VALUES (3, 5);
INSERT INTO experiment_datasources (experiement_id, datasource_id) VALUES (3, 2);

/* Insert experiment version data */
INSERT INTO versions (ver_id, ver_number) VALUES (1, 1);

/* Link versions to experiments */
INSERT INTO experiment_versions (experiment_id, version_id) VALUES (1, 1);

/* Outcomes */
INSERT INTO outcomes (outcome_id, outcome_loc, outcome_type) VALUES (1, '/resources/tempJson/testJSON.json/', 'Data');
INSERT INTO outcomes (outcome_id, outcome_loc, outcome_type) VALUES (2, 'outcomes/log.txt', 'Log');
INSERT INTO outcomes (outcome_id, outcome_loc, outcome_type) VALUES (3, '/resources/tempJson/testJSON2.json/', 'Data');

/* Link outcomes to versions */
INSERT INTO version_outcomes (ver_id, out_id) VALUES (1, 1);
INSERT INTO version_outcomes (ver_id, out_id) VALUES (1, 2);
INSERT INTO version_outcomes (ver_id, out_id) VALUES (1, 3);

/*** System Settings ***/

/* Slave configuration */
INSERT INTO executors (id, address, username, password) VALUES (1, 'http://localhost:8080', 'user', 'password');
