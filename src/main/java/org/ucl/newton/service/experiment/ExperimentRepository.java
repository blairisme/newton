package org.ucl.newton.service.experiment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.Experiment;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Instances of this class provide access to persisted experiment data.
 *
 * @author John Wilkie
 */
@Repository
public class ExperimentRepository {

    private SessionFactory sessionFactory;

    @Inject
    public ExperimentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly=true)
    public Collection<Experiment> getExperimentsForProject(String projectName) {
        Session session = getSession();
        return null;
    }

    @Transactional(readOnly=true)
    public Experiment getExperimentById(int id){
        Session session = getSession();
        return session.get(Experiment.class, id);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}

