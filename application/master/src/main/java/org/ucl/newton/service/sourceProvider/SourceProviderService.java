package org.ucl.newton.service.sourceProvider;

import org.ucl.newton.framework.SourceProvider;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Instances of this class provide access to persisted source provider data.
 *
 * @author Xiaolong Chen
 */
@Named
public class SourceProviderService {
    private SourceProviderRepository repository;

    @Inject
    public SourceProviderService(SourceProviderRepository repository) {
        this.repository = repository;
    }

    public SourceProvider addSourceProvider(SourceProvider sourceProvider) {
        return repository.addSourceProvider(sourceProvider);
    }

    public Collection<SourceProvider> getSourceProviders() {
        return repository.getSourceProviders();
    }

    public SourceProvider getSourceProviderById(int id) {
        SourceProvider sourceProvider = repository.getSourceProviderById(id);
        if (sourceProvider == null) {
            throw new UnknownSourceProviderException(id);
        }
        return sourceProvider;
    }
    
}
