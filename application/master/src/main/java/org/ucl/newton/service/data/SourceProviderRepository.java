package org.ucl.newton.service.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.SourceProvider;

import javax.inject.Inject;

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
        String sql = String.format("SELECT * FROM source_providers");
        NativeQuery query = session.createNativeQuery(sql).addEntity(SourceProvider.class);
        List<SourceProvider> result = query.list();
        return result;
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
