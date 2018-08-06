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
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Instances of this class provide access to persisted project data.
 *
 * @author Blair Butterworth
 * @author John Wilkie
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
    public Project addProject(Project project) {
        Session session = getSession();
        Integer generatedId = (Integer)session.save(project);
        return project.setId(generatedId);
    }

    @Transactional(readOnly=true)
    public Project getProjectById(int id) {
        Session session = getSession();
        return session.get(Project.class, id);
    }

    @Transactional(readOnly=true)
    public Project getProjectByIdentifier(String identifier) {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);
        Root<Project> projects = criteria.from(Project.class);

        criteria.select(projects);
        criteria.where(builder.equal(projects.get("identifier"), identifier));

        return session.createQuery(criteria).getSingleResult();
    }

    @Transactional(readOnly=true)
    public Project getProjectEagerlyByIdentifier(String identifier) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);
        Root<Project> projects = criteria.from(Project.class);
        criteria.select(projects);
        criteria.where(builder.equal(projects.get("identifier"), identifier));
        Project project = session.createQuery(criteria).getSingleResult();
        project.getMembers().size();
        project.getMembersThatStar().size();
        project.getDataSources().size();
        return project;
    }

    @Transactional(readOnly=true)
    public List<Project> getProjects(User user) {
        Session session = getSession();
        String sql = String.format("SELECT * FROM projects AS p INNER JOIN project_membership AS pm " +
                "ON p.id = pm.project_id WHERE pm.user_id = %s", user.getId());
        NativeQuery query = session.createNativeQuery(sql).addEntity(Project.class);
        List<Project> result = query.list();
        return result;
    }

    @Transactional
    public void removeProject(Project project) {
        Session session = getSession();
        session.delete(project);
    }

    @Transactional
    public void updateProject(Project project) {
        Session session = getSession();
        session.update(project);
    }

    @Transactional
    public void mergeProject(Project project) {
        Session session = getSession();
        session.merge(project);
    }

    @Transactional(readOnly=true)
    public List<Project> getProjectsStarredByUser(User user) {
        Session session = getSession();
        String sql = String.format("SELECT * FROM projects AS p INNER JOIN project_starred AS ps " +
                "ON p.id = ps.project_id WHERE ps.user_id = %s", user.getId());
        NativeQuery query = session.createNativeQuery(sql).addEntity(Project.class);
        List<Project> result = query.list();
        return result;
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}


