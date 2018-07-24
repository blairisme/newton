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
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Instances of this class represent a user.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Entity
@Indexed
@Table(name = "users")
public class User implements Serializable
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Field
    @Column(name = "name")
    private String name;

    @Field
    @Column(name = "email")
    private String email;

    @Field
    @Column(name = "image")
    private String image;

    public User() {
        this(0, "", "", "");
    }

    public User(String name, String email, String image) {
        this(0, name, email, image);
    }

    public User(int id, String name, String email, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public User setId(int id) {
        this.id = id;
        return this;
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
            .append(name, user.name)
            .append(email, user.email)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(email)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("email", email)
            .toString();
    }
}
