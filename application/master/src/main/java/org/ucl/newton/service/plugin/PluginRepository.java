/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.plugin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

/**
 * Instances of this class provide access to persisted plugin data.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 */
@Repository
public class PluginRepository
{
    private SessionFactory sessionFactory;

    @Inject
    public PluginRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Transactional
    public Plugin addPlugin(Plugin project) {
        Session session = getSession();
        Integer generatedId = (Integer)session.save(project);
        return project.setId(generatedId);
    }

    @Transactional(readOnly=true)
    public Collection<Plugin> getPlugins() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Plugin> criteria = builder.createQuery(Plugin.class);
        criteria.from(Plugin.class);
        return session.createQuery(criteria).getResultList();
    }

    @Transactional
    public void removePlugin(Plugin plugin) {
        Session session = getSession();
        session.delete(plugin);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
