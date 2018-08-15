/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.user;

import org.apache.lucene.search.Query;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryContextBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

/**
 * Instances of this class provide access to persisted user data.
 *
 * @author Blair Butterworth
 */
@Repository
public class UserRepository
{
    private SessionFactory sessionFactory;

    @Inject
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public User addUser(User user) {
        Session session = getSession();
        Integer generatedId = (Integer)session.save(user);
        return user.setId(generatedId);
    }

    @Transactional(readOnly=true)
    public User getUser(int id) {
        Session session = getSession();
        return session.get(User.class, id);
    }

    @Transactional(readOnly=true)
    public User getUserByEmail(String email) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> users = criteria.from(User.class);

        criteria.select(users);
        criteria.where(builder.equal(users.get("email"), email));

        return getSingleResultOrNull(session.createQuery(criteria));
    }

    @Transactional(readOnly=true)
    public Collection<User> getUsers() {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> users = criteria.from(User.class);
        criteria.select(users);

        return session.createQuery(criteria).getResultList();
    }

    @Transactional(readOnly=true)
    public Collection<User> getUsers(String matching) {
        Session session = getSession();
        FullTextSession searchSession = Search.getFullTextSession(session);

        SearchFactory searchFactory = searchSession.getSearchFactory();
        QueryContextBuilder queryBuilder = searchFactory.buildQueryBuilder();

        Query query = queryBuilder
            .forEntity(User.class)
            .get()
            .keyword()
            .onField("name")
            .matching(matching)
            .createQuery();

        FullTextQuery fullTextQuery = searchSession.createFullTextQuery(query, User.class);
        return fullTextQuery.getResultList();
    }

    @Transactional
    public void removeUser(User user) {
        Session session = getSession();
        session.delete(user);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private <T> T getSingleResultOrNull(org.hibernate.query.Query<T> query){
        List<T> results = query.getResultList();
        if (results.isEmpty()) return null;
        else if (results.size() == 1) return results.get(0);
        throw new NonUniqueResultException(results.size());
    }
}
