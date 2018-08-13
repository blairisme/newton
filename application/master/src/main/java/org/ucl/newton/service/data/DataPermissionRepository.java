package org.ucl.newton.service.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.framework.DataPermission;
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
public class DataPermissionRepository {

    private SessionFactory sessionFactory;

    @Inject
    public DataPermissionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public DataPermission addPermission(DataPermission dataPermission) {
        Session session = getSession();
        Integer generatedId = (Integer)session.save(dataPermission);
        return dataPermission.setId(generatedId);
    }

    @Transactional
    public void removePermission(DataPermission dataPermission) {
        Session session = getSession();
        session.remove(dataPermission);
    }

    @Transactional
    public DataPermission getPermissionByIdEagerly(int id) {
        Session session = getSession();
        DataPermission foundDataPermission = session.get(DataPermission.class, id);
        if(foundDataPermission != null) {
            foundDataPermission.getGrantedPermissions().size();
        }
        return foundDataPermission;
    }

    @Transactional
    public DataPermission getPermissionByDsIdentifierEagerly(String dsIdent) {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<DataPermission> criteria = builder.createQuery(DataPermission.class);
        Root<DataPermission> permissions = criteria.from(DataPermission.class);
        criteria.select(permissions);
        criteria.where(builder.equal(permissions.get("dataSourceIdentifier"), dsIdent));
        DataPermission foundDataPermission = session.createQuery(criteria).getSingleResult();
        if(foundDataPermission != null){
            foundDataPermission.getGrantedPermissions().size();
        }
        return foundDataPermission;
    }

    @Transactional
    public void updatePermission(DataPermission dataPermission) {
        Session session = getSession();
        session.update(dataPermission);
    }

    @Transactional
    public Collection<DataPermission> getPermissionsOwnedByUserEagerly(User user) {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<DataPermission> criteria = builder.createQuery(DataPermission.class);
        Root<DataPermission> permissions = criteria.from(DataPermission.class);
        criteria.select(permissions);
        criteria.where(builder.equal(permissions.get("owner"), user.getId()));
        Collection<DataPermission> foundDataPermissions = session.createQuery(criteria).getResultList();

        for(DataPermission dataPermission : foundDataPermissions) {
            dataPermission.getGrantedPermissions().size();
        }

        return foundDataPermissions;
    }

    @Transactional
    public Collection<DataPermission> getPermissionsGrantedToUser(User user) {
        Session session = getSession();

        String sql = String.format("SELECT * FROM dataPermissions AS p INNER JOIN permission_granted AS pg " +
                "ON p.permission_id = pg.permission_id WHERE pg.user_id = '%s'", user.getId());
        NativeQuery<DataPermission> query = session.createNativeQuery(sql).addEntity(DataPermission.class);
        return query.list();
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

}
