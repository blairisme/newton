/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.common;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides utility functions for working with the
 * {@link WebDriver Selenium WebDriver}.
 *
 * @author Blair Butterworth
 */
public class WebDriverUtils
{
    public static void disableLogging(WebDriver driver) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
    }

    public static boolean elementExists(WebDriver driver, By by) {
        List<WebElement> elements = driver.findElements(by);
        return !elements.isEmpty();
    }

    public static String getSpanText(WebElement element) {
        return element.getAttribute("innerText");
    }
}
