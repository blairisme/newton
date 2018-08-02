package org.ucl.newton.common.identifier;

import org.junit.Assert;
import org.junit.Test;

public class IdentifierTest
{
    @Test
    public void createTest() {
        String name = "Fizzyo heart rate versus weather experiment";
        String expected = "Fizzyo-heart-rate-versus-weather-experiment";
        String actual = Identifier.create(name);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void longTest() {
        String name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                " sed do eiusmod tempor incididunt ut labore et dolore magna " +
                "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis " +
                "aute irure dolor in reprehenderit in voluptate velit esse " +
                "cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                "occaecat cupidatat non proident, sunt in culpa qui officia" +
                " deserunt mollit anim id est laborum.";
        String expected = "Lorem-ipsum-dolor-sit-amet-consectetur-adipiscing-elit-sed-do-eiusmod-tempor-incididunt-ut-labore-et";
        String actual = Identifier.create(name);
        Assert.assertEquals(expected, actual);
    }
}
