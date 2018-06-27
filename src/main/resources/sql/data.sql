/* Add test users */
INSERT INTO `USERS` VALUES ('user', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'Test User', 'CONTRIBUTOR');
INSERT INTO `USERS` VALUES ('admin', '$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m', 'Admin User', 'ADMINISTRATOR');

/* Add test projects */
INSERT INTO `PROJECTS` (`id`, `name`, `description`, `updated`) VALUES ('project-fizzyo', 'Project Fizzyo', 'Project Fizzyo Description', '2018-06-20 12:34:56');
INSERT INTO `PROJECTS` (`id`, `name`, `description`, `updated`) VALUES ('cancer-research', 'Cancer Research Trial 4', 'Cancer Research Trial 4 Description', '2018-05-19 11:12:13');
INSERT INTO `PROJECTS` (`id`, `name`, `description`, `updated`) VALUES ('aids-research', 'AIDS Research', 'AIDS Research Description', '2018-04-07 10:09:08');