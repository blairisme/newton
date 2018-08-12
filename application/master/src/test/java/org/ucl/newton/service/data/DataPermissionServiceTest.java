package org.ucl.newton.service.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.framework.DataPermission;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.data.DataPermissionRepository;
import org.ucl.newton.service.data.DataPermissionService;

import java.util.ArrayList;
import java.util.Collection;


public class DataPermissionServiceTest {

    private DataPermissionRepository repository;
    private DataPermissionService service;
    private DataPermission fizzyoDataPermission;
    private DataPermission weatherDataPermission;
    private User adminUser;

    @Before
    public void setUp() {
        repository = Mockito.mock(DataPermissionRepository.class);
        service = new DataPermissionService(repository);
        adminUser = new User(2, "admin", "admin@ucl.ac.uk", "pp_4.jpg");
        fizzyoDataPermission = createFizzyoPermission();
        weatherDataPermission = createWeatherPermission();
    }

    @Test
    public void testAddPermission() {
        Mockito.when(repository.addPermission(fizzyoDataPermission)).thenReturn(fizzyoDataPermission);
        Assert.assertEquals(fizzyoDataPermission, service.addPermission(fizzyoDataPermission));
    }

    @Test
    public void testGetPermissionsOwnerByUser() {
        Collection<DataPermission> ownedDataPermissions = new ArrayList<>();
        ownedDataPermissions.add(fizzyoDataPermission);
        Mockito.when(repository.getPermissionsOwnedByUserEagerly(adminUser)).thenReturn(ownedDataPermissions);

        Collection actualPermissions = service.getPermissionsOwnedByUser(adminUser);
        Assert.assertEquals(ownedDataPermissions, actualPermissions);
    }

    @Test
    public void testGetPermissionsGrantedToUser() {
        Collection<DataPermission> grantedDataPermissions = new ArrayList<>();
        grantedDataPermissions.add(weatherDataPermission);
        Mockito.when(repository.getPermissionsGrantedToUser(adminUser)).thenReturn(grantedDataPermissions);

        Collection actualPermissions = service.getPermissionsGrantedToUser(adminUser);
        Assert.assertEquals(grantedDataPermissions, actualPermissions);
    }

    @Test
    public void testGetAllPermissionsForUSer() {
        Collection<DataPermission> ownedDataPermissions = new ArrayList<>();
        ownedDataPermissions.add(fizzyoDataPermission);
        Mockito.when(repository.getPermissionsOwnedByUserEagerly(adminUser)).thenReturn(ownedDataPermissions);

        Collection<DataPermission> grantedDataPermissions = new ArrayList<>();
        grantedDataPermissions.add(weatherDataPermission);
        Mockito.when(repository.getPermissionsGrantedToUser(adminUser)).thenReturn(grantedDataPermissions);

        Collection<DataPermission> actualDataPermissions = service.getAllPermissionsForUser(adminUser);
        Assert.assertEquals(2, actualDataPermissions.size());
        Assert.assertTrue(actualDataPermissions.contains(weatherDataPermission));
        Assert.assertTrue(actualDataPermissions.contains(fizzyoDataPermission));
    }

    private DataPermission createFizzyoPermission() {
        Collection<User> grantedPermissions = createGrantedUserCollection();
        return new DataPermission(2, adminUser, "newton-fizzyo", grantedPermissions);
    }

    private DataPermission createWeatherPermission() {
        User owner = new User(1, "user", "user@ucl.ac.uk", "pp_1.jpg");
        Collection<User> grantedPermissions = new ArrayList<>();
        grantedPermissions.add(adminUser);
        return new DataPermission(2, owner, "newton-weather", grantedPermissions);
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
