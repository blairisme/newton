/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URL;

/**
 * Unit tests for the {@link ApplicationUrls} class.
 *
 * @author Blair Butterworth
 */
public class ApplicationUrlsTest
{
    @Test
    public void getFilesUrlTest() throws Exception {
        ApplicationPreferences preferences = Mockito.mock(ApplicationPreferences.class);
        Mockito.when(preferences.getApplicationHost()).thenReturn("newton.com");
        Mockito.when(preferences.getApplicationPort()).thenReturn(1234);

        ApplicationUrls applicationUrls = new ApplicationUrls(preferences);

        URL expected = new URL("http://newton.com:1234/files");
        URL actual = applicationUrls.getFilesUrl();

        Assert.assertEquals(expected, actual);
    }
}
