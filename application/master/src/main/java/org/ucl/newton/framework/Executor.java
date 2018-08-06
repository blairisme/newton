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

/**
 * Instances of this class store slave connection details.
 *
 * @author Blair Butterworth
 */
@Entity
@Table(name = "executors")
public class Executor
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "address")
    private String address;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public Executor() {
    }

    public Executor(String address, String username, String password) {
        this.id = 0;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        Executor that = (Executor)obj;
        return new EqualsBuilder()
            .append(this.address, that.address)
            .append(this.username, that.username)
            .append(this.password, that.password)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(address)
            .append(username)
            .append(password)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("address", address)
            .append("username", username)
            .append("password", password)
            .toString();
    }
}
