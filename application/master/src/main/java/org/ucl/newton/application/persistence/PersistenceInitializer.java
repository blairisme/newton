/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Instances of this class initialize the persistence system, ensuring the text
 * search index is up to date.
 *
 * @author Blair Butterworth
 */
@Service
@Transactional
@SuppressWarnings("unused")
public class PersistenceInitializer implements ApplicationListener<ContextRefreshedEvent>
{
    private SessionFactory sessionFactory;

    @Inject
    public PersistenceInitializer(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Async
    @Transactional
    public void initialize() {
        try {
            Session session = sessionFactory.getCurrentSession();
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            fullTextSession.createIndexer().startAndWait();
        }
        catch (InterruptedException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initialize();
    }
}
