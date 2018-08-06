package org.ucl.newton.service.experiment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.Experiment;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Instances of this class provide access to persisted experiment data.
 *
 * @author John Wilkie
 * @author Blair Butterworth
 */
@Repository
public class ExperimentRepository {

    private SessionFactory sessionFactory;

    @Inject
    public ExperimentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Experiment addExperiment(Experiment experiment) {
        Session session = getSession();
        Integer generatedId = (Integer)session.save(experiment);
        return experiment.setId(generatedId);
    }

    @Transactional(readOnly=true)
    public Collection<Experiment> getExperiments() {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Experiment> criteria = builder.createQuery(Experiment.class);
        Root<Experiment> experiments = criteria.from(Experiment.class);
        criteria.select(experiments);

        return session.createQuery(criteria).getResultList();
    }

    @Transactional(readOnly=true)
    public Collection<Experiment> getExperimentsForProject(String projectIdentifier) {
        Session session = getSession();
        String sql = String.format("SELECT * FROM experiments AS e INNER JOIN projects AS p " +
                "ON e.project_id = p.id WHERE p.identifier = '%s'", projectIdentifier);
        NativeQuery<Experiment> query = session.createNativeQuery(sql).addEntity(Experiment.class);
        return query.list();
    }

    @Transactional(readOnly=true)
    public Experiment getExperimentById(int id){
        Session session = getSession();
        return session.get(Experiment.class, id);
    }

    @Transactional(readOnly=true)
    public Experiment getExperimentByIdentifier(String identifier) {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Experiment> criteria = builder.createQuery(Experiment.class);
        Root<Experiment> experiments = criteria.from(Experiment.class);

        criteria.select(experiments);
        criteria.where(builder.equal(experiments.get("identifier"), identifier));

        return session.createQuery(criteria).getSingleResult();
    }

    @Transactional
    public void update(Experiment experiment) {
        Session session = getSession();
        session.update(experiment);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}

