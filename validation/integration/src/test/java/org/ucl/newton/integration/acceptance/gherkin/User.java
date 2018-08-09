/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.gherkin;

import org.ucl.newton.integration.acceptance.newton.user.UserDto;

/**
 * Contains information about a user account in the Newton system.
 *
 * @author Blair Butterworth
 */
public class User
{
    private String email;
    private String password;
    private String fullName;

    public User() {
    }

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public UserDto asUserDto() {
        return new UserDto(email, password, fullName);
    }
}
