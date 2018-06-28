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
import java.util.Collection;

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
    public void addUser(User user) {
        Session session = getSession();
        session.save(user);
    }

    @Transactional(readOnly=true)
    public User getUser(String id) {
        Session session = getSession();
        return session.get(User.class, id);
    }

    @Transactional(readOnly=true)
    public Collection<User> findUsers(String matching) {
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
}
