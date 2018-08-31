package org.ucl.newton.framework;

/**
 * Defines the role of a user
 *
 * @author John Wilkie
 */
public enum UserRole {
    API ("API"),
    ADMIN ("ADMIN"),
    ORGANISATIONLEAD ("ORGANISATIONLEAD"),
    USER ("USER");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    /*Manual  ("Manual"),
    Onchange("On data change");

    private final String name;

    ExperimentTriggerType(String name) {
        this.name = name;
    }*/
}
