package org.ucl.newton.service.permisson;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Permission;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.permission.PermissionRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class PermissionRepositoryTest {

    @Inject
    private PermissionRepository repository;

    @Test
    public void testAddRemovePermission() {
        User userBlair = new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", "profile.jpg");
        Permission newPermission = new Permission(8, userBlair, "new-data-source", new ArrayList<>());

        int id = repository.addPermission(newPermission).getId();
        Permission newPermissionRetrieved = repository.getPermissionByIdEagerly(id);
        Assert.assertEquals(newPermission, newPermissionRetrieved);

        repository.removePermission(newPermission);
        Assert.assertNull(repository.getPermissionByIdEagerly(id));
    }

    @Test
    public void testUpdatePermission() {
        User userXialong = new User(4, "Xiaolong Chen", "xiaolong.chen@ucl.ac.uk", "pp_2.jpg");

        Permission permission = repository.getPermissionByIdEagerly(2);
        Collection<User> grantedPermissions = permission.getGrantedPermissions();
        Assert.assertFalse(grantedPermissions.contains(userXialong));

        grantedPermissions.add(userXialong);
        permission.setGrantedPermissions(grantedPermissions);
        repository.updatePermission(permission);

        permission = repository.getPermissionByIdEagerly(2);
        grantedPermissions = permission.getGrantedPermissions();
        Assert.assertTrue(grantedPermissions.contains(userXialong));
    }

    @Test
    public void testGetPermissionsOwnedByUser() {
        User adminUser = new User(2, "admin", "admin@ucl.ac.uk", "pp_4.jpg");
        User userUser = new User(1, "user", "user@ucl.ac.uk", "pp_1.jpg");

        Collection<Permission> permissions = repository.getPermissionsOwnedByUserEagerly(adminUser);
        Assert.assertEquals(2, permissions.size());

        permissions = repository.getPermissionsOwnedByUserEagerly(userUser);
        Assert.assertEquals(0, permissions.size());
    }

    @Test
    public void testGetPermissionsGrantedToAUser() {
        User userBlair = new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", "profile.jpg");
        User userZiad = new User(5, "Ziad Al Halabi", "ziad.halabi.17@ucl.ac.uk", "pp_3.jpg");
        User userJohn = new User(6, "John Wilkie", "john.wilkie.17@ucl.ac.uk", "pp_1.jpg");

        Collection<Permission> permissions = repository.getPermissionsGrantedToUser(userBlair);
        Assert.assertEquals(2, permissions.size());

        permissions = repository.getPermissionsGrantedToUser(userZiad);
        Assert.assertEquals(1, permissions.size());

        permissions = repository.getPermissionsGrantedToUser(userJohn);
        Assert.assertEquals(0, permissions.size());
    }

}
