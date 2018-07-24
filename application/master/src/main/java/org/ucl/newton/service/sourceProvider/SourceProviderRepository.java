package org.ucl.newton.service.sourceProvider;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.SourceProvider;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Instances of this class provide access to persisted source provider data.
 *
 * @author Xiaolong Chen
 */
@Repository
public class SourceProviderRepository {
    private SessionFactory sessionFactory;

    @Inject
    public SourceProviderRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public SourceProvider addSourceProvider(SourceProvider sourceProvider) {
        Session session = getSession();
        Integer generatedId = (Integer)session.save(sourceProvider);
        return sourceProvider.setId(generatedId);
    }

    @Transactional(readOnly=true)
    public SourceProvider getSourceProviderById(int id) {
        Session session = getSession();
        return session.get(SourceProvider.class, id);
    }


    @Transactional(readOnly=true)
    public List<SourceProvider> getSourceProviders() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<SourceProvider> criteria = builder.createQuery(SourceProvider.class);
        criteria.from(SourceProvider.class);
        return session.createQuery(criteria).getResultList();
    }

    @Transactional
    public void removeSourceProvider(SourceProvider sourceProvider) {
        Session session = getSession();
        session.delete(sourceProvider);
    }

    @Transactional
    public void updateSourceProvider(SourceProvider sourceProvider) {
        Session session = getSession();
        session.update(sourceProvider);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

}
