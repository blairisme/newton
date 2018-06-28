/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.project;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.Project;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    @Transactional
    public void addProject(Project project) {
        Session session = getSession();
        session.save(project);
    }

    @Transactional(readOnly=true)
    public Project getProject(String id) {
        Session session = getSession();
        return session.get(Project.class, id);
    }

    @Transactional(readOnly=true)
    public List<Project> getProjects(int index, int size) {
        Session session = getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<Project> from = criteriaQuery.from(Project.class);
        CriteriaQuery<Project> select = criteriaQuery.select(from);

        TypedQuery<Project> typedQuery = session.createQuery(select);
        typedQuery.setFirstResult(index);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

    @Transactional
    public void removeProject(Project project) {
        Session session = getSession();
        session.delete(project);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}


