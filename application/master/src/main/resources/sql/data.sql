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
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (12, 2, 'ucl-camelot', 'UCL Project Camelot', 'Project description', 'ucl.png', '2017-07-07 10:09:08');
INSERT INTO projects (id, owner_id, identifier, name, description, image, updated) VALUES (13, 2, 'gosh-apollo', 'GOSH Project Apollo', 'Project description', 'default.png', '2017-08-07 10:09:08');

/* Project membership */
INSERT INTO project_membership (project_id, user_id) VALUES (1, 1);
INSERT INTO project_membership (project_id, user_id) VALUES (1, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (1, 3);
INSERT INTO project_membership (project_id, user_id) VALUES (1, 4);
INSERT INTO project_membership (project_id, user_id) VALUES (1, 5);
INSERT INTO project_membership (project_id, user_id) VALUES (2, 1);
INSERT INTO project_membership (project_id, user_id) VALUES (2, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (3, 2);
INSERT INTO project_membership (project_id, user_id) VALUES (3, 3);
INSERT INTO project_membership (project_id, user_id) VALUES (3, 5);
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
INSERT INTO project_starred (project_id, user_id) VALUES (3, 5);
INSERT INTO project_starred (project_id, user_id) VALUES (4, 2);
INSERT INTO project_starred (project_id, user_id) VALUES (2, 2);
INSERT INTO project_starred (project_id, user_id) VALUES (8, 2);
INSERT INTO project_starred (project_id, user_id) VALUES (13, 2);

/* Project data sources */
INSERT INTO project_datasources (pds_id, pds_project, pds_datasource) VALUES (1, 1, 'newton-weather');
INSERT INTO project_datasources (pds_id, pds_project, pds_datasource) VALUES (2, 1, 'newton-fizzyo');

/* Insert into storage configuration */
INSERT INTO storage_configuration (sc_id, sc_type, sc_location, sc_initial_script) VALUES (1, 'Newton', 'classpath:/experiment/experiment-1/repository', 'main.ipynb');

/* Insert into experiment configurations */
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (1, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (2, 1, 'newton-ruby', '/*.csv', 'Onchange');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (3, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (4, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (5, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (6, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (7, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (8, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (9, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (10, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (11, 1, 'newton-python', '/*.csv', 'Manual');
INSERT INTO experiment_config (exp_config_id, storage_config_id, exp_proc_engine, exp_out_pattern, exp_trigger) VALUES (12, 1, 'newton-python', '/*.csv', 'Manual');

/* Experiments */
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (1, 'experiment-1', 'HR classification', 'Experiment description', 1, 3, 1);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (2, 'experiment-2', 'Exercise level classification', 'Experiment description', 1, 4, 2);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (3, 'experiment-3', 'Weather temperature classification', 'Experiment description', 1, 5, 3);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (4, 'experiment-4', 'Weather data aggrigator', 'Experiment description', 1, 3, 4);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (5, 'experiment-5', 'HR normalisation', 'Experiment description', 1, 2, 5);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (6, 'experiment-6', 'Adherence rate classification', 'Experiment description', 1, 1, 6);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (7, 'experiment-7', 'Achievement rate classification', 'Experiment description', 1, 2, 7);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (8, 'experiment-8', 'Weather percipitation classification', 'Experiment description', 1, 4, 8);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (9, 'experiment-9', 'Weather humidity classification', 'Experiment description', 1, 4, 9);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (10, 'experiment-10', 'ACT adherence by type', 'Experiment description', 1, 5, 10);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (11, 'experiment-11', 'Impact of gamification on adherence', 'Experiment description', 1, 2, 11);
INSERT INTO experiments (exp_id, exp_identifier, exp_name, exp_description, project_id, creator_id, exp_config_id) VALUES (12, 'experiment-12', 'Gamification impact by game', 'Experiment description', 1, 1, 12);

/* Insert into experiment data sources */
INSERT INTO eds (eds_id, ds_id, eds_custom_location) VALUES (1, 'weather', 'someloc/input.csv');

/* Insert into join table between experiments and experiment data sources */
INSERT INTO experiment_eds_link (exp_config_id, eds_id) VALUES (1, 1);
INSERT INTO experiment_eds_link (exp_config_id, eds_id) VALUES (2, 1);
INSERT INTO experiment_eds_link (exp_config_id, eds_id) VALUES (3, 1);

/* Insert experiment version data */
INSERT INTO versions (ver_id, ver_number) VALUES (1, 1);

/* Link versions to experiments */
INSERT INTO experiment_versions (experiment_id, version_id) VALUES (1, 1);

/* Outcomes */
INSERT INTO outcomes (outcome_id, outcome_name, outcome_location, outcome_type) VALUES (1, 'test1.json', 'tempJson/testJSON.json', 'Data');
INSERT INTO outcomes (outcome_id, outcome_name, outcome_location, outcome_type) VALUES (2, 'log.txt', 'demo/log.txt', 'Log');
INSERT INTO outcomes (outcome_id, outcome_name, outcome_location, outcome_type) VALUES (3, 'test2.json', 'tempJson/testJSON2.json', 'Data');

/* Link outcomes to versions */
INSERT INTO version_outcomes (ver_id, out_id) VALUES (1, 1);
INSERT INTO version_outcomes (ver_id, out_id) VALUES (1, 2);
INSERT INTO version_outcomes (ver_id, out_id) VALUES (1, 3);

/*** System Settings ***/

/* Slave configuration */
INSERT INTO executors (id, address, username, password) VALUES (1, 'http://localhost:8080', 'user', 'password');

INSERT INTO plugin (id, identifier, location) VALUES (1, 'newton-python', 'classpath:/plugins/processor/python.jar');
INSERT INTO plugin (id, identifier, location) VALUES (2, 'newton-jupyter', 'classpath:/plugins/processor/jupyter.jar');
INSERT INTO plugin (id, identifier, location) VALUES (3, 'newton-weather', 'classpath:/plugins/data/WeatherDataProvider.jar');
INSERT INTO plugin (id, identifier, location) VALUES (4, 'newton-fizzyo', 'classpath:/plugins/data/FizzyoDataProvider.jar');

