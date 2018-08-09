/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.springframework.boot.SpringApplication;

/**
 * Entry point for the slave web application.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
public class ApplicationLauncher
{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private ApplicationLauncher() {
        throw new UnsupportedOperationException();
    }
}
