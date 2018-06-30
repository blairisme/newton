/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.apache.commons.lang3.Validate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Instances of this class construct {@link Project} instances.
 *
 * @author Blair Butterworth
 */
public class ProjectBuilder
{
    private String identifier;
    private String name;
    private String description;
    private Date updated;
    private User owner;
    private Collection<User> members;

    public ProjectBuilder() {
        this.description = "";
        this.updated = new Date();
        this.members = Collections.emptyList();
    }

    public void generateIdentifier(String text) {
        try {
            this.identifier = URLEncoder.encode(text, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setMembers(Collection<User> members) {
        this.members = members;
    }

    public Project build() {
        Validate.notNull(identifier);
        Validate.notNull(name);
        Validate.notNull(owner);
        return new Project(0, identifier, name, description, updated, owner, members);
    }
}
