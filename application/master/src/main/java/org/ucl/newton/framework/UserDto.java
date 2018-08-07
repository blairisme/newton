package org.ucl.newton.framework;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Data transfer object for user
 *
 * @author John Wilkie
 */
public class UserDto {

    @NotEmpty
    private String fullName;

    @NotEmpty
    private String password;

    @Email
    @NotEmpty
    private String email;

    public void setFullName(String name) {
        fullName = name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
