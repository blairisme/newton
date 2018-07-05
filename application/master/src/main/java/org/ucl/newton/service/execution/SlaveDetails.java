/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

/**
 * Instances of this class store slave connection details.
 *
 * @author Blair Butterworth
 */
public class SlaveDetails
{
    private String address;
    private String username;
    private String password;

    public SlaveDetails(String address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
