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
    private String link;
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

    public void generateLink(String text) {
        try {
            this.link = URLEncoder.encode(text, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setLink(String link) {
        this.link = link;
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
        Validate.notNull(link);
        Validate.notNull(name);
        Validate.notNull(owner);
        return new Project(0, link, name, description, updated, owner, members);
    }
}
