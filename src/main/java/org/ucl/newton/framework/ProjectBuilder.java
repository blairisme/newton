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
import java.util.Date;

/**
 * Instances of this class construct {@link Project} instances.
 *
 * @author Blair Butterworth
 */
public class ProjectBuilder
{
    private String id;
    private String name;
    private String description;
    private Date updated;
    private User owner;

    public void generateId(String text) {
        try {
            this.id = URLEncoder.encode(text, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
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

    public Project build() {
        Validate.notNull(id);
        Validate.notNull(name);
        Validate.notNull(description);
        Validate.notNull(updated);
        Validate.notNull(owner);
        return new Project(id, name, description, updated, owner);
    }
}
