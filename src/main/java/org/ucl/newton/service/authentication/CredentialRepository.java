package org.ucl.newton.service.authentication;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.Credential;

import javax.inject.Inject;

public class CredentialRepository
{
    private SessionFactory sessionFactory;

    @Inject
    public CredentialRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void addCredential(Credential credential) {
        Session session = getSession();
        session.save(credential);
    }

    @Transactional(readOnly=true)
    public Credential getCredential(String id) {
        Session session = getSession();
        return session.get(Credential.class, id);
    }

    @Transactional
    public void removeCredential(Credential credential) {
        Session session = getSession();
        session.delete(credential);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
