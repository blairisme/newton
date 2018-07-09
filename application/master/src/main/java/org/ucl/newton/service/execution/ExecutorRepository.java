/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.Executor;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

/**
 * Instances of this class provide access to persisted slave connection
 * details.
 *
 * @author Blair Butterworth
 */
@Repository
public class ExecutorRepository
{
    private SessionFactory sessionFactory;

    @Inject
    public ExecutorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public Collection<Executor> getExecutors() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Executor> criteria = builder.createQuery(Executor.class);
        criteria.from(Executor.class);
        return session.createQuery(criteria).getResultList();
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
