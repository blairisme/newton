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
 * Instances of this class represent a data process, a container for
 * a link to a github repository and a definition of which script to
 * initially call within that repository.
 *
 * @author John Wilkie
 * @author Blair Butterworth
 */
@Entity
@Table(name = "process")
public class DataProcessor
{
    @Id
    @Column(name = "proc_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "proc_repo_url")
    private String repoUrl;

    @Column(name = "proc_initial_script")
    private String nameOfInitialScript;

    @Column(name = "proc_engine")
    private String processEngine;

    public DataProcessor() {
    }

    public DataProcessor(
        int id,
        String repoUrl,
        String nameOfInitialScript,
        String processEngine)
    {
        this.id = id;
        this.repoUrl = repoUrl;
        this.nameOfInitialScript = nameOfInitialScript;
        this.processEngine = processEngine;
    }

    public int getId() {
        return id;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public String getNameOfInitialScript() {
        return nameOfInitialScript;
    }

    public String getProcessEngine() {
        return processEngine;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        DataProcessor other = (DataProcessor)obj;
        return new EqualsBuilder()
            .append(this.id, other.id)
            .append(this.repoUrl, other.repoUrl)
            .append(this.nameOfInitialScript, other.nameOfInitialScript)
            .append(this.processEngine, other.processEngine)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(repoUrl)
            .append(nameOfInitialScript)
            .append(processEngine)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("repoUrl", repoUrl)
            .append("nameOfInitialScript", nameOfInitialScript)
            .append("processEngine", processEngine)
            .toString();
    }
}
