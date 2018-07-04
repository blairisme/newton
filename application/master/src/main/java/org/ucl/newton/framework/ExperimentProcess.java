package org.ucl.newton.framework;

import javax.persistence.*;

/**
 * Instances of this class represent a data process, a container for
 * a link to a github repository and a definition of witch script to
 * initially call within that repository.
 *
 * @author John Wilkie
 */

@Entity
@Table(name = "process")
public class ExperimentProcess {

    @Id
    @Column(name = "proc_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "proc_repo_url")
    private String repoUrl;

    @Column(name = "proc_initial_script")
    private String nameOfInitialScript;

    @Column(name = "proc_engine")
    private String processEngine; // Should probably change from string at some point

    public ExperimentProcess() {}

    public ExperimentProcess(
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

    public int getId() { return id; }

    public String getRepoUrl() { return repoUrl; }

    public String getNameOfInitialScript() { return nameOfInitialScript; }

    public String getProcessEngine() { return processEngine; }
}
