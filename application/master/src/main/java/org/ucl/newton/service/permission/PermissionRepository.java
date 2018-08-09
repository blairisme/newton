package org.ucl.newton.service.permission;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.Permission;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Instances of this class provide access to persisted permission data.
 *
 * @author John Wilkie
 */

@Repository
public class PermissionRepository {

    private SessionFactory sessionFactory;

    @Inject
    public PermissionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Permission addPermission(Permission permission) {
        Session session = getSession();
        Integer generatedId = (Integer)session.save(permission);
        return permission.setId(generatedId);
    }

    @Transactional
    public void removePermission(Permission permission) {
        Session session = getSession();
        session.remove(permission);
    }

    @Transactional
    public Permission getPermissionByIdEagerly(int id) {
        Session session = getSession();
        Permission foundPermission = session.get(Permission.class, id);
        if(foundPermission != null) {
            foundPermission.getGrantedPermissions().size();
        }
        return foundPermission;
    }

    @Transactional
    public void updatePermission(Permission permission) {
        Session session = getSession();
        session.update(permission);
    }

    @Transactional
    public Collection<Permission> getPermissionsOwnedByUserEagerly(User user) {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Permission> criteria = builder.createQuery(Permission.class);
        Root<Permission> permissions = criteria.from(Permission.class);
        criteria.select(permissions);
        criteria.where(builder.equal(permissions.get("owner"), user.getId()));
        Collection<Permission> foundPermissions = session.createQuery(criteria).getResultList();

        for(Permission permission: foundPermissions) {
            permission.getGrantedPermissions().size();
        }

        return foundPermissions;
    }

    @Transactional
    public Collection<Permission> getPermissionsGrantedToUser(User user) {
        Session session = getSession();

        String sql = String.format("SELECT * FROM permissions AS p INNER JOIN permission_granted AS pg " +
                "ON p.permission_id = pg.permission_id WHERE pg.user_id = '%s'", user.getId());
        NativeQuery<Permission> query = session.createNativeQuery(sql).addEntity(Permission.class);
        return query.list();
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

}
