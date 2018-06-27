/* Create users table to contain user accounts */

CREATE TABLE IF NOT EXISTS `USERS` (
  `id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(80) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

/* Create projects table to store project information */

CREATE TABLE IF NOT EXISTS `PROJECTS` (
  `id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `updated` DATETIME NOT NULL,
  PRIMARY KEY (`id`));