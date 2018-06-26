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
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import java.io.Serializable;

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

    public void save(User user) {
        Session session = getSession();
        session.save(user);
    }

    public User read(long id) {
        return getSession().get(User.class, id);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
