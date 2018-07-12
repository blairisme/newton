package org.ucl.newton.framework;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Instances of this class provide represent a source provider.
 *
 * @author Xiaolong Chen
 */

@Entity
@Table(name = "source_providers")
public class SourceProvider implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "jarPath")
    private String jarPath;

    @Column(name = "providerName")
    private String providerName;

    @Column(name = "version")
    private String version;

    public SourceProvider(int id, String jarPath, String providerName,String version) {
        this.id = id;
        this.jarPath = jarPath;
        this.providerName = providerName;
        this.version = version;
    }

    public int getId() { return id; }

    public SourceProvider setId(int id) {
        this.id = id;
        return this;
    }

    public String getJarPath() { return jarPath; }

    public void setJarPath(String jarPath) { this.jarPath = jarPath; }

    public String getProviderName() { return providerName; }

    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getVersion() { return version; }

    public void setVersion(String version) { this.version = version; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SourceProvider sourceProvider = (SourceProvider) obj;
        return new EqualsBuilder()
                .append(id, sourceProvider.id)
                .append(jarPath, sourceProvider.jarPath)
                .append(providerName, sourceProvider.providerName)
                .append(version, sourceProvider.version)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(jarPath)
                .append(providerName)
                .append(version)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("jarPath", jarPath)
                .append("providerName", providerName)
                .append("version", version)
                .toString();
    }
}
