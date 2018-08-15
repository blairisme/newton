/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.authentication;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.Credential;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Instances of this class provide access to persisted credential data.
 *
 * @author Blair Butterworth
 */
@Repository
public class CredentialRepository
{
    private SessionFactory sessionFactory;

    @Inject
    public CredentialRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Credential addCredential(Credential credential) {
        Session session = getSession();
        Integer generatedId = (Integer)session.save(credential);
        return credential.setId(generatedId);
    }

    @Transactional(readOnly=true)
    public Credential getCredentialById(int id) {
        Session session = getSession();
        return session.get(Credential.class, id);
    }

    @Transactional(readOnly=true)
    public Credential getCredentialByName(String username) {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Credential> criteria = builder.createQuery(Credential.class);
        Root<Credential> credentials = criteria.from(Credential.class);

        criteria.select(credentials);
        criteria.where(builder.equal(credentials.get("username"), username));

        Query<Credential> query = session.createQuery(criteria);
        return getSingleResultOrNull(query);
    }

    private <T> T getSingleResultOrNull(Query<T> query){
        List<T> results = query.getResultList();
        if (results.isEmpty()) return null;
        else if (results.size() == 1) return results.get(0);
        throw new NonUniqueResultException(results.size());
    }

    @Transactional
    public void removeCredential(Credential credential) {
        Session session = getSession();
        session.delete(credential);
    }

    @Transactional
    public void updateCredential(Credential credential) {
        Session session = getSession();
        session.update(credential);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

}
