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

/**
 * Unit tests for the {@link ApplicationPreferences} class.
 *
 * @author Blair Butterworth
 */
public class ApplicationPreferencesTest
{
    @Test
    public void getMasterHostDefaultTest() {
        String expected = "localhost";
        System.clearProperty("newton.master.host");

        ApplicationPreferences preferences = new ApplicationPreferences();
        String actual = preferences.getMasterHost();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMasterHostEnvironmentTest() {
        String expected = "newton.com";
        System.setProperty("newton.master.host", expected);

        ApplicationPreferences preferences = new ApplicationPreferences();
        String actual = preferences.getMasterHost();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMasterPortDefaultTest() {
        int expected = 9090;
        System.clearProperty("newton.master.port");

        ApplicationPreferences preferences = new ApplicationPreferences();
        int actual = preferences.getMasterPort();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMasterPortEnvironmentTest() {
        int expected = 1234;
        System.setProperty("newton.master.port", Integer.toString(expected));

        ApplicationPreferences preferences = new ApplicationPreferences();
        int actual = preferences.getMasterPort();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getApplicationHostDefaultTest() {
        String expected = "localhost";
        System.clearProperty("newton.application.host");

        ApplicationPreferences preferences = new ApplicationPreferences();
        String actual = preferences.getApplicationHost();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getApplicationHostEnvironmentTest() {
        String expected = "newton.com";
        System.setProperty("newton.application.host", expected);

        ApplicationPreferences preferences = new ApplicationPreferences();
        String actual = preferences.getApplicationHost();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getApplicationPortDefaultTest() {
        int expected = 8080;
        System.clearProperty("newton.application.port");

        ApplicationPreferences preferences = new ApplicationPreferences();
        int actual = preferences.getApplicationPort();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getApplicationPortEnvironmentTest() {
        int expected = 5678;
        System.setProperty("newton.application.port", Integer.toString(expected));

        ApplicationPreferences preferences = new ApplicationPreferences();
        int actual = preferences.getApplicationPort();

        Assert.assertEquals(expected, actual);
    }
}
