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

/* Create users table to contain user accounts */

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

/* Create credentials table to contain user passwords */

CREATE TABLE IF NOT EXISTS credentials (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(80) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

/* Create projects table to store project information */

CREATE TABLE IF NOT EXISTS projects (
    id INT NOT NULL AUTO_INCREMENT,
    owner_id INT NOT NULL,
    identifier VARCHAR(45) NOT NULL,
    name VARCHAR(45) NOT NULL,
    description VARCHAR(200) NOT NULL,
    image VARCHAR(45) NOT NULL,
    updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users(id)
);

/* Create join table to connect users to a project as members */
CREATE TABLE IF NOT EXISTS project_membership (
    project_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (project_id, user_id),
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES projects(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

/* Create join table to connect users that have stare a project */
CREATE TABLE IF NOT EXISTS project_starred (
    project_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (project_id, user_id),
    CONSTRAINT fk_project_star FOREIGN KEY (project_id) REFERENCES projects(id),
    CONSTRAINT fk_user_star FOREIGN KEY (user_id) REFERENCES users(id)
);

/* Create experiment table to store data relating to experiments */
CREATE TABLE IF NOT EXISTS experiments (
    exp_id INT NOT NULL AUTO_INCREMENT,
    exp_name VARCHAR(45) NOT NULL,
    parentProject_id INT NOT NULL,
    creator_id INT NOT NULL,
    PRIMARY KEY (exp_id),
    CONSTRAINT fk_experiment_parent FOREIGN KEY (parentProject_id) REFERENCES projects(id),
    CONSTRAINT fk_experiment_creator FOREIGN KEY (creator_id) REFERENCES users(id)
);

/* Create table to store data relating to data sources */
CREATE TABLE IF NOT EXISTS datasources (
    ds_id INT NOT NULL AUTO_INCREMENT,
    ds_name VARCHAR(45) NOT NULL,
    ds_version INT NOT NULL,
    ds_data_location VARCHAR(100) NOT NULL,
    PRIMARY KEY (ds_id)
);

/* Create table to store data relating to the script to run*/
CREATE TABLE IF NOT EXISTS process (
    proc_id INT NOT NULL AUTO_INCREMENT,
    proc_repo_url VARCHAR(100) NOT NULL,
    proc_initial_script VARCHAR(100) NOT NULL,
    proc_engine VARCHAR(45) NOT NULL,
    PRIMARY KEY (proc_id)
);

/* Create table to store experiment versions */
CREATE TABLE IF NOT EXISTS versions (
    ver_id INT NOT NULL AUTO_INCREMENT,
    ver_number INT NOT NULL,
    ver_name VARCHAR(45) NOT NULL,
    process_id INT NOT NULL,
    PRIMARY KEY (ver_id),
    CONSTRAINT fk_versions_process FOREIGN KEY (process_id) REFERENCES process(proc_id)
);

/* Create table that stores the relationship between experiments and versions */
CREATE TABLE IF NOT EXISTS experiment_versions (
    experiment_id INT NOT NULL,
    version_id INT NOT NULL,
    PRIMARY KEY (experiment_id, version_id),
    CONSTRAINT fk_experiment_versions_exp FOREIGN  KEY (experiment_id) REFERENCES experiments(exp_id),
    CONSTRAINT fk_experiment_versions_ver FOREIGN  KEY (version_id) REFERENCES  versions(ver_id)
);

/* Create table to link versions to the data sources used */
CREATE TABLE IF NOT EXISTS version_data_sources (
    ver_id INT NOT NULL,
    ds_id INT NOT NULL,
    PRIMARY KEY (ver_id, ds_id),
    CONSTRAINT fk_vds_ver FOREIGN KEY (ver_id) REFERENCES  versions(ver_id),
    CONSTRAINT fk_vds_ds FOREIGN KEY (ds_id) REFERENCES  datasources(ds_id),
);

/* Create table for experiment outcomes */
CREATE TABLE IF NOT EXISTS outcomes (
    outcome_id INT NOT NULL AUTO_INCREMENT,
    outcome_loc VARCHAR(100) NOT NULL,
    outcome_type VARCHAR NOT NULL,
    PRIMARY KEY (outcome_id)
);

/* Create table to link outcomes to versions */
CREATE TABLE IF NOT EXISTS version_outcomes (
    ver_id INT NOT NULL,
    out_id INT NOT NULL,
    PRIMARY KEY (ver_id, out_id),
    CONSTRAINT fk_vo_ver FOREIGN KEY (ver_id) REFERENCES  versions(ver_id),
    CONSTRAINT fk_vo_out FOREIGN KEY (out_id) REFERENCES  outcomes(outcome_id),
);
