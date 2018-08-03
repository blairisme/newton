package org.ucl.newton.sdk.publisher;

import org.ucl.newton.sdk.plugin.NewtonPlugin;

public interface DataPublisher extends NewtonPlugin {
    void start(String filePath);
    String getConfigName();
    Class<?> getConfigClass();
    <T> void setConfig(T config);

}
