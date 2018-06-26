/* Create USERS table to contain user accounts */

CREATE TABLE IF NOT EXISTS `USERS` (
  `id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(80) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
