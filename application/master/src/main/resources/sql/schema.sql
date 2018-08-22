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

/*** Users and authentication ***/

/* Create users table to contain user accounts */
CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    image VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

/* Create credentials table to contain user passwords */
CREATE TABLE IF NOT EXISTS credentials (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(80) NOT NULL,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_credentials_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


/*** Projects ***/

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
    CONSTRAINT fk_project_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

/* Create join table to connect users to a project as members */
CREATE TABLE IF NOT EXISTS project_membership (
    project_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (project_id, user_id),
    CONSTRAINT fk_membership_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    CONSTRAINT fk_membership_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

/* Create join table to connect users that have stare a project */
CREATE TABLE IF NOT EXISTS project_starred (
    project_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (project_id, user_id),
    CONSTRAINT fk_starred_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    CONSTRAINT fk_starred_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

/* Create join table to connect selected data sources to a project */
CREATE TABLE IF NOT EXISTS project_datasources (
    pds_id INT NOT NULL AUTO_INCREMENT,
    pds_project INT NOT NULL,
    pds_datasource VARCHAR(100) NOT NULL,
    PRIMARY KEY (pds_id),
    CONSTRAINT fk_projds_project FOREIGN KEY (pds_project) REFERENCES projects(id) ON DELETE CASCADE
);


/*** Experiments ***/
/* Create table to store an experiments storage configuration */
CREATE TABLE IF NOT EXISTS storage_configuration (
    sc_id INT NOT NULL AUTO_INCREMENT,
    sc_type VARCHAR(20) NOT NULL,
    sc_location VARCHAR(200) NOT NULL,
    sc_initial_script VARCHAR(100) NOT NULL,
    PRIMARY KEY (sc_id)
);

/* Creates table for experiment configurations */
CREATE TABLE IF NOT EXISTS experiment_config (
    exp_config_id INT NOT NULL AUTO_INCREMENT,
    storage_config_id INT NOT NULL,
    exp_proc_engine VARCHAR(45) NOT NULL,
    exp_out_pattern VARCHAR(100) NOT NULL,
    exp_display_pattern VARCHAR(100) NOT NULL,
    exp_trigger VARCHAR(20) NOT NULL,
    PRIMARY KEY (exp_config_id),
    CONSTRAINT fk_experiment_storage FOREIGN KEY (storage_config_id) REFERENCES storage_configuration(sc_id) ON DELETE CASCADE
);

/* Create experiment table to store data relating to experiments */
CREATE TABLE IF NOT EXISTS experiments (
    exp_id INT NOT NULL AUTO_INCREMENT,
    exp_identifier VARCHAR(45) NOT NULL,
    exp_name VARCHAR(45) NOT NULL,
    exp_description VARCHAR(500),
    project_id INT NOT NULL,
    creator_id INT NOT NULL,
    exp_config_id INT NOT NULL,
    PRIMARY KEY (exp_id),
    CONSTRAINT fk_experiment_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    CONSTRAINT fk_experiment_creator FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_experiment_config FOREIGN KEY (exp_config_id) REFERENCES experiment_config(exp_config_id) ON DELETE CASCADE
);

/* Creates table for experiment data sources */
CREATE TABLE IF NOT EXISTS eds (
    eds_id INT NOT NULL AUTO_INCREMENT,
    ds_id VARCHAR(50) NULL,
    eds_custom_location VARCHAR(100) NOT NULL,
    PRIMARY KEY (eds_id)
);

/* Create table to link experiment configuration to the experiment data sources configuration */
CREATE TABLE IF NOT EXISTS experiment_eds_link (
    exp_config_id INT NOT NULL,
    eds_id INT NOT NULL,
    PRIMARY KEY (exp_config_id, eds_id),
    CONSTRAINT fk_experiementconfig FOREIGN KEY (exp_config_id) REFERENCES experiment_config(exp_config_id) ON DELETE CASCADE,
    CONSTRAINT fk_datasource FOREIGN KEY (eds_id) REFERENCES eds(eds_id) ON DELETE CASCADE
);

/* Create table to store experiment versions */
CREATE TABLE IF NOT EXISTS versions (
    ver_id INT NOT NULL AUTO_INCREMENT,
    ver_number INT NOT NULL,
    ver_created DATETIME NOT NULL,
    ver_duration BIGINT NOT NULL,
    PRIMARY KEY (ver_id)
);

/* Create table that stores the relationship between experiments and versions */
CREATE TABLE IF NOT EXISTS experiment_versions (
    experiment_id INT NOT NULL,
    version_id INT NOT NULL,
    PRIMARY KEY (experiment_id, version_id),
    CONSTRAINT fk_experiment_versions_exp FOREIGN KEY (experiment_id) REFERENCES experiments(exp_id) ON DELETE CASCADE,
    CONSTRAINT fk_experiment_versions_ver FOREIGN KEY (version_id) REFERENCES versions(ver_id) ON DELETE CASCADE
);

/* Create table for experiment outcomes */
CREATE TABLE IF NOT EXISTS outcomes (
    outcome_id INT NOT NULL AUTO_INCREMENT,
    outcome_name VARCHAR(100) NOT NULL,
    outcome_location VARCHAR(200) NOT NULL,
    outcome_type VARCHAR(20) NOT NULL,
    PRIMARY KEY (outcome_id)
);

/* Create table to link outcomes to versions */
CREATE TABLE IF NOT EXISTS version_outcomes (
    ver_id INT NOT NULL,
    out_id INT NOT NULL,
    PRIMARY KEY (ver_id, out_id),
    CONSTRAINT fk_vo_ver FOREIGN KEY (ver_id) REFERENCES versions(ver_id) ON DELETE CASCADE,
    CONSTRAINT fk_vo_out FOREIGN KEY (out_id) REFERENCES outcomes(outcome_id) ON DELETE CASCADE
);

/* Create table to contain data source provider details */
CREATE TABLE IF NOT EXISTS source_providers (
  id INT NOT NULL AUTO_INCREMENT,
  jarPath VARCHAR(100) NOT NULL,
  providerName VARCHAR(100) NOT NULL,
  version VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

/*** System Settings ***/

/* Create table to contain slave details */
CREATE TABLE IF NOT EXISTS executors (
    id INT NOT NULL AUTO_INCREMENT,
    address VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS plugin (
  id INT NOT NULL AUTO_INCREMENT,
  location VARCHAR(200) NOT NULL,
  PRIMARY KEY (id)
);

/*** Data Permissions ***/

/* Create table to contain dataPermissions data */
CREATE TABLE IF NOT EXISTS dataPermissions (
  permission_id INT NOT NULL AUTO_INCREMENT,
  permission_owner INT NOT NULL,
  permission_ds_ident VARCHAR(100) NOT NULL,
  PRIMARY KEY (permission_id),
  CONSTRAINT fk_permissions_owner FOREIGN KEY (permission_owner) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS permission_granted (
  permission_id INT NOT NULL,
  user_id INT NOT NULL,
  PRIMARY KEY (permission_id, user_id),
  CONSTRAINT fk_permission_granted_permission_id FOREIGN KEY (permission_id) REFERENCES dataPermissions(permission_id) ON DELETE CASCADE,
  CONSTRAINT fk_permission_granted_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);