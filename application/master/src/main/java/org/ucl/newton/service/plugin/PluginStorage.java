/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.plugin;

import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.common.file.NamedSteamProvider;
import org.ucl.newton.sdk.plugin.PluginHostStorage;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Allows plugins to persist data in the Newton system.
 *
 * @author Blair Butterworth
 */
@Named
public class PluginStorage implements PluginHostStorage
{
    private NamedSteamProvider steamProvider;

    @Inject
    public PluginStorage(ApplicationStorage applicationStorage) {
        this.steamProvider = new NamedSteamProvider(applicationStorage.getPluginConfigDirectory());
    }

    @Override
    public InputStream getInputStream(String id) throws IOException {
        return steamProvider.getInputStream(id);
    }

    @Override
    public OutputStream getOutputStream(String id) throws IOException {
        return steamProvider.getOutputStream(id);
    }
}
