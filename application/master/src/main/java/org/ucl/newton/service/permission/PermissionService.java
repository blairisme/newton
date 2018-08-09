package org.ucl.newton.service.permission;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.ucl.newton.framework.Permission;
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
public class PermissionService {

    private PermissionRepository repository;

    @Inject
    public PermissionService(PermissionRepository repository) {
        this.repository = repository;
    }

    public Permission addPermission(Permission permission) {
        Validate.notNull(permission);
        return repository.addPermission(permission);
    }

    public void removePermission(Permission permission) {
        Validate.notNull(permission);
        repository.removePermission(permission);
    }

    public void updatePermission(Permission permission) {
        Validate.notNull(permission);
        repository.updatePermission(permission);
    }

    public Collection<Permission> getPermissionsOwnedByUser(User user) {
        Validate.notNull(user);
        return repository.getPermissionsOwnedByUserEagerly(user);
    }

    public Collection<Permission> getPermissionsGrantedToUser(User user) {
        Validate.notNull(user);
        return repository.getPermissionsGrantedToUser(user);
    }

    public Collection<Permission> getAllPermissionsForUser(User user) {
        Validate.notNull(user);
        Collection<Permission> granted = repository.getPermissionsGrantedToUser(user);
        Collection<Permission> owner = repository.getPermissionsOwnedByUserEagerly(user);
        Collection<Permission> joined = new ArrayList<>(granted);

        for(Permission permission: owner) {
            if(!joined.contains(permission)){
                joined.add(permission);
            }
        }

        return joined;
    }

}
