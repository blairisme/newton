package org.ucl.newton.common.network;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UrlUtilsTest
{
    @Test
    public void createUrlTest() throws MalformedURLException {
        URL base = new URL("http://localhost:8080/files");
        Path path = Paths.get("experiment/1/foo.zip");

        URL expected = new URL("http://localhost:8080/files/experiment/1/foo.zip");
        URL actual = UrlUtils.createUrl(base, path);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void windowsPathTest() throws MalformedURLException {
        URL base = new URL("http://localhost:8080/files");
        Path path = Paths.get("experiment\\1\\foo.zip");

        URL expected = new URL("http://localhost:8080/files/experiment/1/foo.zip");
        URL actual = UrlUtils.createUrl(base, path);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void leadingSlashTest() throws MalformedURLException {
        URL base = new URL("http://localhost:8080/files");
        Path path = Paths.get("/experiment/1/foo.zip");

        URL expected = new URL("http://localhost:8080/files/experiment/1/foo.zip");
        URL actual = UrlUtils.createUrl(base, path);

        Assert.assertEquals(expected, actual);
    }
}
