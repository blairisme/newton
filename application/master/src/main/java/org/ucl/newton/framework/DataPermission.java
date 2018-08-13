package org.ucl.newton.framework;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Collection;

/**
 * Instance of this class represent the permission connection between users and data.
 *
 * @author John Wilkie
 */

@Entity
@Table(name = "dataPermissions")
public class DataPermission {

    @Id
    @Column(name = "permission_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @ManyToOne
    @JoinColumn(name = "permission_owner")
    private User owner;

    @Column(name = "permission_ds_ident")
    private String dataSourceIdentifier;

    @OneToMany
    @JoinTable(name = "permission_granted",
            joinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Collection<User> grantedPermissions;

    public DataPermission() { }

    public DataPermission(
        int id,
        User owner,
        String dataSourceIdentifier,
        Collection<User> grantedPermissions)
    {
        this.id = id;
        this.owner = owner;
        this.dataSourceIdentifier = dataSourceIdentifier;
        this.grantedPermissions = grantedPermissions;
    }

    public DataPermission setId(int id) {
        this.id = id;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getDataSourceIdentifier() {
        return dataSourceIdentifier;
    }

    public User getOwner() {
        return owner;
    }

    public Collection<User> getGrantedPermissions() {
        return grantedPermissions;
    }

    public void setGrantedPermissions(Collection<User> grantedPermissions) {
        this.grantedPermissions = grantedPermissions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        DataPermission that = (DataPermission)obj;
        return new EqualsBuilder()
            .append(this.id, that.id)
            .append(this.owner, that.owner)
            .append(this.dataSourceIdentifier, that.dataSourceIdentifier)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(owner)
            .append(dataSourceIdentifier)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("owner", owner)
            .append("dataSourceIdentifier", dataSourceIdentifier)
            .append( "userWithGrantedPermissions", grantedPermissions)
            .toString();
    }
}
