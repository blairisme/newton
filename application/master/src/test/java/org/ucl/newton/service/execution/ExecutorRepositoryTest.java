/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Executor;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class ExecutorRepositoryTest
{
    @Inject
    private ExecutorRepository repository;

    @Test
    public void getExecutorsTest() {
        Collection<Executor> expected = Arrays.asList(new Executor("http://localhost:8080", "user", "password"));
        Collection<Executor> actual = repository.getExecutors();
        Assert.assertEquals(expected, actual);
    }
}
