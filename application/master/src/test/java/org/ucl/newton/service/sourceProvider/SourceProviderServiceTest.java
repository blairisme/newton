package org.ucl.newton.service.sourceProvider;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.project.ProjectRepository;
import org.ucl.newton.service.project.ProjectService;

public class SourceProviderServiceTest {
    @Test
    public void getSourceProvidersTest() {
        User user = Mockito.mock(User.class);
        SourceProviderRepository repository = Mockito.mock(SourceProviderRepository.class);

        SourceProviderService sourceProviderService = new SourceProviderService(repository);
        Assert.assertNotNull(sourceProviderService.getSourceProviders());
    }
}
