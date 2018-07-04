package org.ucl.newton.service.experiment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentProcess;
import org.ucl.newton.framework.ExperimentVersion;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

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
    public Collection<Experiment> getExperimentsForProject(String projectIdentifier) {
        Session session = getSession();
        String sql = String.format("SELECT * FROM experiments AS e INNER JOIN projects AS p " +
                "ON e.parentProject_id = p.id WHERE p.identifier = '%s'", projectIdentifier);
        NativeQuery query = session.createNativeQuery(sql).addEntity(Experiment.class);
        List<Experiment> result = query.list();
        return result;
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

