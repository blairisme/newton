/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Instances of this class represent a user.
 *
 * @author Blair Butterworth
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable
{
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(Long id, String username, String password, String displayName, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    @Deprecated
    public String getName() {
        return displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        User user = (User)obj;
        return new EqualsBuilder()
            .append(id, user.id)
            .append(displayName, user.displayName)
            .append(username, user.username)
            .append(password, user.password)
            .append(role, user.role)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(displayName)
            .append(username)
            .append(password)
            .append(role)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("username", username)
            .append("displayName", displayName)
            .append("role", role)
            .toString();
    }
}
