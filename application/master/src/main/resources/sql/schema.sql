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

CREATE INDEX ix_user_name ON users(name);

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
    PRIMARY KEY (exp_id),
    CONSTRAINT fk_project_parent FOREIGN KEY (parentProject_id) REFERENCES projects(id)
);
