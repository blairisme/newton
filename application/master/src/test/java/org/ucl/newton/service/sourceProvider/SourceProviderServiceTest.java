package org.ucl.newton.service.sourceProvider;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.framework.User;

public class SourceProviderServiceTest {
    @Test
    public void getSourceProvidersTest() {
        SourceProviderRepository repository = Mockito.mock(SourceProviderRepository.class);

        SourceProviderService sourceProviderService = new SourceProviderService(repository);
        Assert.assertNotNull(sourceProviderService.getSourceProviders());
    }
}
