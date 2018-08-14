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
        User userBlair = new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", "profile.jpg");
        DataPermission newDataPermission = new DataPermission(8, userBlair, "new-data-source", new ArrayList<>());

        int id = repository.addPermission(newDataPermission).getId();
        DataPermission newDataPermissionRetrieved = repository.getPermissionByIdEagerly(id);
        Assert.assertEquals(newDataPermission, newDataPermissionRetrieved);

        repository.removePermission(newDataPermission);
        Assert.assertNull(repository.getPermissionByIdEagerly(id));
    }

    @Test
    public void testUpdatePermission() {
        User userXialong = new User(4, "Xiaolong Chen", "xiaolong.chen@ucl.ac.uk", "pp_2.jpg");

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
        User adminUser = new User(2, "admin", "admin@ucl.ac.uk", "pp_4.jpg");
        User userUser = new User(1, "user", "user@ucl.ac.uk", "pp_1.jpg");

        Collection<DataPermission> dataPermissions = repository.getPermissionsOwnedByUserEagerly(adminUser);
        Assert.assertEquals(2, dataPermissions.size());

        dataPermissions = repository.getPermissionsOwnedByUserEagerly(userUser);
        Assert.assertEquals(0, dataPermissions.size());
    }

    @Test
    public void testGetPermissionsGrantedToAUser() {
        User userBlair = new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", "profile.jpg");
        User userZiad = new User(5, "Ziad Al Halabi", "ziad.halabi.17@ucl.ac.uk", "pp_3.jpg");
        User userJohn = new User(6, "John Wilkie", "john.wilkie.17@ucl.ac.uk", "pp_1.jpg");

        Collection<DataPermission> dataPermissions = repository.getPermissionsGrantedToUser(userBlair);
        Assert.assertEquals(2, dataPermissions.size());

        dataPermissions = repository.getPermissionsGrantedToUser(userZiad);
        Assert.assertEquals(1, dataPermissions.size());

        dataPermissions = repository.getPermissionsGrantedToUser(userJohn);
        Assert.assertEquals(0, dataPermissions.size());
    }

}
