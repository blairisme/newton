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
  id VARCHAR(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  description VARCHAR(200) NOT NULL,
  updated DATETIME NOT NULL,
  owner_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users(id)
);