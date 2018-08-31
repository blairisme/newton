package org.ucl.newton.api.experiment;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.newton.api.user.UserDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExperimentDtoTest {

    @Test
    public void testEquality() {
        ExperimentDto dto1 = createDto("experiment-1");
        ExperimentDto dto2 = createDto("experiment-2");

        Assert.assertNotEquals(null, dto1);
        Assert.assertEquals(dto1, dto1);
        Assert.assertNotEquals(dto1, new UserDto());
        Assert.assertNotEquals(dto1, dto2);
        Assert.assertNotEquals(dto1.hashCode(), dto2.hashCode());

    }

    @Test
    public void testToString() {
        ExperimentDto experimentDto1 = createDto("name");
        String in = "Item(s): [item1.test],[item2.qa],[item3.production]";

        Pattern p = Pattern.compile("\\[(.*?)\\]");
        Matcher m = p.matcher(experimentDto1.toString());

        while(m.find()) {
            Assert.assertEquals("identifier=name,name=Name,description=Description,creator=Ziad," +
                    "project=project-1,trigger=Manual,processor=python,storage=newton,outputPattern=*.csv," +
                    "displayPattern=*.html,dataSourceLocs={loc2,loc1},dataSourceIds={weather.csv}",m.group(1));
        }

    }

    private ExperimentDto createDto(String identifier) {
        return new ExperimentDto(
                identifier,
                "Name",
                "Description",
                "Ziad",
                "project-1",
                "Manual",
                "python",
                "newton",
                "*.csv",
                "*.html",
                new String[]{"loc2", "loc1"},
                new String[]{"weather.csv"});
    }
}
