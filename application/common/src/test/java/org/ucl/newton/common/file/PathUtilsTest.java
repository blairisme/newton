/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Unit tests for the {@link PathUtils} class.
 *
 * @author Blair Butterworth
 */
public class PathUtilsTest
{
    private Path tempDirectory;

    @Before
    public void setup() {
        tempDirectory = SystemPaths.getTempDirectory(getClass().getName());
    }

    @After
    public void tearDown() throws IOException {
        org.apache.commons.io.FileUtils.deleteDirectory(tempDirectory.toFile());
    }

    @Test
    public void appendTrailingSeparatorTest() {
        String path = "/test/foo";
        String expected = "/test/foo" + File.separator;
        String actual = PathUtils.appendTrailingSeparator(path);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findChildrenTest() throws IOException {
        Path file1 = tempDirectory.resolve("foo.txt");
        Path file2 = tempDirectory.resolve("bar.png");
        Path file3 = tempDirectory.resolve("waz.jpg");
        Path file4 = tempDirectory.resolve("test.gif");

        FileUtils.createNew(file1.toFile());
        FileUtils.createNew(file2.toFile());
        FileUtils.createNew(file3.toFile());
        FileUtils.createNew(file4.toFile());

        List<Path> expected = Arrays.asList(file1, file4);
        List<Path> actual = new ArrayList<>(PathUtils.findChildren(tempDirectory, Arrays.asList("*.txt", "test.*")));

        Collections.sort(expected);
        Collections.sort(actual);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createNewTest() throws IOException {
        Path newPath = tempDirectory.resolve("test1");
        File newFile = newPath.toFile();
        Assert.assertFalse(newFile.exists());

        PathUtils.createNew(newPath);
        Assert.assertTrue(newFile.exists());
    }

    @Test
    public void getNameTest() {
        Path path = tempDirectory.resolve("/test/foo.txt");
        String expected = "foo.txt";
        String actual = PathUtils.getName(path);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getNamesTest() {
        Path path1 = tempDirectory.resolve("foo.txt");
        Path path2 = tempDirectory.resolve("bar.txt");
        Path path3 = tempDirectory.resolve("waz.txt");

        Collection<Path> paths = Arrays.asList(path1, path2, path3);
        Collection<String> expected = Arrays.asList("foo.txt", "bar.txt", "waz.txt");
        Collection<String> actual = PathUtils.getNames(paths);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void resolveTest() {
        Path path1 = tempDirectory.resolve("foo.txt");
        Path path2 = tempDirectory.resolve("bar.txt");
        Path path3 = tempDirectory.resolve("waz.txt");

        Collection<Path> children = Arrays.asList(path1.getFileName(), path2.getFileName(), path3.getFileName());
        Collection<Path> expected = Arrays.asList(path1, path2, path3);
        Collection<Path> actual = PathUtils.resolve(tempDirectory, children);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void toFileTest() {
        Path path1 = tempDirectory.resolve("foo.txt");
        Path path2 = tempDirectory.resolve("bar.txt");
        Path path3 = tempDirectory.resolve("waz.txt");

        Collection<Path> paths = Arrays.asList(path1, path2, path3);
        Collection<File> expected = Arrays.asList(path1.toFile(), path2.toFile(), path3.toFile());
        Collection<File> actual = PathUtils.toFile(paths);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Ignore
    public void toURLTest() throws Exception {
        Path path = Paths.get("/test/foo.txt");
        URL expected = new URL("file:/test/foo.txt");
        URL actual = PathUtils.toUrl(path);
        Assert.assertEquals(expected, actual);
    }
}
