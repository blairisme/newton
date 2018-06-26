/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.ucl.newton.framework.Project;

import javax.inject.Inject;

/**
 * Instances of this class provide access to persisted project data.
 *
 * @author Blair Butterworth
 */
@Repository
public class ProjectRepository
{
    private SessionFactory sessionFactory;

    @Inject
    public ProjectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Project project) {
        getSession().save(project);
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

//    public Employee findById(final Serializable id) {
//        return getSession().get(Employee.class, id);
//    }
}


