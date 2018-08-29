package org.ucl.newton.service.data;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.DataPermission;
import org.ucl.newton.framework.User;
import org.ucl.newton.testobjects.DummyUserFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class DataPermissionRepositoryTest {

    @Inject
    private DataPermissionRepository repository;

    @Test
    public void testAddRemovePermission() {
        User userBlair = DummyUserFactory.createUserBlair();
        DataPermission newDataPermission = new DataPermission(8, userBlair, "new-data-source", new ArrayList<>());

        int id = repository.addPermission(newDataPermission).getId();
        DataPermission newDataPermissionRetrieved = repository.getPermissionByIdEagerly(id);
        Assert.assertEquals(newDataPermission, newDataPermissionRetrieved);

        repository.removePermission(newDataPermission);
        Assert.assertNull(repository.getPermissionByIdEagerly(id));
    }

    @Test
    public void testUpdatePermission() {
        User userXialong = DummyUserFactory.createUserXiaolong();

        DataPermission dataPermission = repository.getPermissionByIdEagerly(2);
        Collection<User> grantedPermissions = dataPermission.getGrantedPermissions();
        Assert.assertFalse(grantedPermissions.contains(userXialong));

        grantedPermissions.add(userXialong);
        dataPermission.setGrantedPermissions(grantedPermissions);
        repository.updatePermission(dataPermission);

        dataPermission = repository.getPermissionByIdEagerly(2);
        grantedPermissions = dataPermission.getGrantedPermissions();
        Assert.assertTrue(grantedPermissions.contains(userXialong));
    }

    @Test
    public void testGetPermissionsOwnedByUser() {
        User adminUser = DummyUserFactory.createUserAdmin();
        User userUser = DummyUserFactory.createUserUser();

        Collection<DataPermission> dataPermissions = repository.getPermissionsOwnedByUserEagerly(adminUser);
        Assert.assertEquals(7, dataPermissions.size());

        dataPermissions = repository.getPermissionsOwnedByUserEagerly(userUser);
        Assert.assertEquals(0, dataPermissions.size());
    }

    @Test
    public void testGetPermissionsGrantedToAUser() {
        User userBlair = DummyUserFactory.createUserBlair();
        User userZiad = DummyUserFactory.createUserZiad();
        User userJohn = DummyUserFactory.createUserJohn();

        Collection<DataPermission> dataPermissions = repository.getPermissionsGrantedToUser(userBlair);
        Assert.assertEquals(4, dataPermissions.size());

        dataPermissions = repository.getPermissionsGrantedToUser(userZiad);
        Assert.assertEquals(2, dataPermissions.size());

        dataPermissions = repository.getPermissionsGrantedToUser(userJohn);
        Assert.assertEquals(0, dataPermissions.size());
    }

}
