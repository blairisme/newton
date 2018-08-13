package org.ucl.newton.service.data;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.ucl.newton.framework.DataPermission;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this interface provide access to permissions data.
 *
 * @author John Wilkie
 */

@Service
public class DataPermissionService {

    private DataPermissionRepository repository;

    @Inject
    public DataPermissionService(DataPermissionRepository repository) {
        this.repository = repository;
    }

    public DataPermission addPermission(DataPermission dataPermission) {
        Validate.notNull(dataPermission);
        return repository.addPermission(dataPermission);
    }

    public Collection<DataPermission> getPermissionsOwnedByUser(User user) {
        Validate.notNull(user);
        return repository.getPermissionsOwnedByUserEagerly(user);
    }

    public Collection<DataPermission> getPermissionsGrantedToUser(User user) {
        Validate.notNull(user);
        return repository.getPermissionsGrantedToUser(user);
    }

    public Collection<DataPermission> getAllPermissionsForUser(User user) {
        Validate.notNull(user);
        Collection<DataPermission> granted = repository.getPermissionsGrantedToUser(user);
        Collection<DataPermission> owner = repository.getPermissionsOwnedByUserEagerly(user);
        Collection<DataPermission> joined = new ArrayList<>(granted);

        for(DataPermission dataPermission : owner) {
            if(!joined.contains(dataPermission)){
                joined.add(dataPermission);
            }
        }

        return joined;
    }

    public DataPermission getPermissionByDataSourceName(String dsIdent) {
        Validate.notNull(dsIdent);
        return repository.getPermissionByDsIdentifierEagerly(dsIdent);
    }

    public void removeGrantedPermission(String dataSourceIdent, User user) {
        Validate.notNull(dataSourceIdent);
        Validate.notNull(user);
        DataPermission permssion = repository.getPermissionByDsIdentifierEagerly(dataSourceIdent);
        Collection<User> granted = permssion.getGrantedPermissions();
        granted.remove(user);
        permssion.setGrantedPermissions(granted);
        repository.updatePermission(permssion);
    }

    public void addGrantedPermission(String dataSourceIdent, User user) {
        Validate.notNull(dataSourceIdent);
        Validate.notNull(user);
        DataPermission permssion = repository.getPermissionByDsIdentifierEagerly(dataSourceIdent);
        Collection<User> granted = permssion.getGrantedPermissions();
        granted.add(user);
        permssion.setGrantedPermissions(granted);
        repository.updatePermission(permssion);
    }
}
