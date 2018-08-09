package org.ucl.newton.service.permisson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.framework.Permission;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.permission.PermissionRepository;
import org.ucl.newton.service.permission.PermissionService;

import java.util.ArrayList;
import java.util.Collection;


public class PermissionServiceTest {

    private PermissionRepository repository;
    private PermissionService service;
    private Permission fizzyoPermission;
    private Permission weatherPermission;
    private User adminUser;

    @Before
    public void setUp() {
        repository = Mockito.mock(PermissionRepository.class);
        service = new PermissionService(repository);
        adminUser = new User(2, "admin", "admin@ucl.ac.uk", "pp_4.jpg");
        fizzyoPermission = createFizzyoPermission();
        weatherPermission = createWeatherPermission();
    }

    @Test
    public void testAddPermission() {
        Mockito.when(repository.addPermission(fizzyoPermission)).thenReturn(fizzyoPermission);
        Assert.assertEquals(fizzyoPermission, service.addPermission(fizzyoPermission));
    }

    @Test
    public void testGetPermissionsOwnerByUser() {
        Collection<Permission> ownedPermissions = new ArrayList<>();
        ownedPermissions.add(fizzyoPermission);
        Mockito.when(repository.getPermissionsOwnedByUserEagerly(adminUser)).thenReturn(ownedPermissions);

        Collection actualPermissions = service.getPermissionsOwnedByUser(adminUser);
        Assert.assertEquals(ownedPermissions, actualPermissions);
    }

    @Test
    public void testGetPermissionsGrantedToUser() {
        Collection<Permission> grantedPermissions = new ArrayList<>();
        grantedPermissions.add(weatherPermission);
        Mockito.when(repository.getPermissionsGrantedToUser(adminUser)).thenReturn(grantedPermissions);

        Collection actualPermissions = service.getPermissionsGrantedToUser(adminUser);
        Assert.assertEquals(grantedPermissions, actualPermissions);
    }

    @Test
    public void testGetAllPermissionsForUSer() {
        Collection<Permission> ownedPermissions = new ArrayList<>();
        ownedPermissions.add(fizzyoPermission);
        Mockito.when(repository.getPermissionsOwnedByUserEagerly(adminUser)).thenReturn(ownedPermissions);

        Collection<Permission> grantedPermissions = new ArrayList<>();
        grantedPermissions.add(weatherPermission);
        Mockito.when(repository.getPermissionsGrantedToUser(adminUser)).thenReturn(grantedPermissions);

        Collection<Permission> actualPermissions = service.getAllPermissionsForUser(adminUser);
        Assert.assertEquals(2, actualPermissions.size());
        Assert.assertTrue(actualPermissions.contains(weatherPermission));
        Assert.assertTrue(actualPermissions.contains(fizzyoPermission));
    }

    private Permission createFizzyoPermission() {
        Collection<User> grantedPermissions = createGrantedUserCollection();
        return new Permission(2, adminUser, "newton-fizzyo", grantedPermissions);
    }

    private Permission createWeatherPermission() {
        User owner = new User(1, "user", "user@ucl.ac.uk", "pp_1.jpg");
        Collection<User> grantedPermissions = new ArrayList<>();
        grantedPermissions.add(adminUser);
        return new Permission(2, owner, "newton-weather", grantedPermissions);
    }

    private Collection<User> createGrantedUserCollection() {
        Collection<User> granted = new ArrayList<>();
        User user1 = new User(1, "user", "user@ucl.ac.uk", "pp_1.jpg");
        User user3 = new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", "profile.jpg");
        granted.add(user1);
        granted.add(user3);
        return granted;
    }
}
